package com.example.jaeyounglibrary.config;

import com.example.jaeyounglibrary.components.CustomAccessDeniedHandler;
import com.example.jaeyounglibrary.components.CustomAuthenticationEntryPoint;
import com.example.jaeyounglibrary.components.CustomSucessHandler;
import com.example.jaeyounglibrary.jwt.JwtFilter;
import com.example.jaeyounglibrary.jwt.JwtUtil;
import com.example.jaeyounglibrary.jwt.LoginFilter;
import com.example.jaeyounglibrary.service.CustomOAuth2UserService;
import com.example.jaeyounglibrary.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

// Spring Security 설정을 위한 구성 클래스입니다.
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	// 의존성 주입을 통해 필요한 컴포넌트 및 유틸리티를 가져옵니다.
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomSucessHandler customSucessHandler;
	private final CustomOAuth2UserService customOauth2UserService;
	
	// AuthenticationManager 빈을 생성합니다.
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// 비밀번호 인코딩을 위한 PasswordEncoder 빈을 생성합니다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public LogoutSuccessHandler logoutHandler(){
		return (request, response, authentication) -> {
			response.setStatus(HttpStatus.OK.value());
			response.getWriter().write("Logout success!!");
			
		};
	}
	
	// SecurityFilterChain 빈을 생성하여 보안 필터 체인 구성을 정의합니다.
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.formLogin(formLogin -> formLogin.disable())
				.httpBasic(httpBasic -> httpBasic.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/api",
								"/api/login",
								"/api/join",
								"/api/reissue",
								"/api/validate-loginId/**",
								"/api/bookList",
								"/api/oauth2/success").permitAll()
						.requestMatchers("/api/admin").hasRole("ADMIN")
						.requestMatchers("/api/rentBook").authenticated() // 인증 필요
						.anyRequest().authenticated()
				)
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(List.of("http://www.jaeyoung.shop"));
					config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					config.setAllowCredentials(true);
					config.addAllowedHeader("*");
					config.addExposedHeader("Authorization");
					return config;
				}))
				
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				
				.addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
				
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(customAuthenticationEntryPoint)
						.accessDeniedHandler(customAccessDeniedHandler)
				);
		http.oauth2Login(oauth2 -> oauth2.userInfoEndpoint((userInfoConfig ->
						userInfoConfig.userService(this.customOauth2UserService)))
				.successHandler(this.customSucessHandler));
		http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), this.jwtUtil),
				UsernamePasswordAuthenticationFilter.class);
		
		http.logout(logout->logout.logoutUrl("/api/logout")
				.logoutSuccessHandler(logoutHandler())
				.addLogoutHandler((request, response, authentication)->{
					if(request.getSession()!=null){
						request.getSession().invalidate();
					}
				}).deleteCookies("JSESSIONID","refresh"));
		
		return http.build();
	}
	
	
}