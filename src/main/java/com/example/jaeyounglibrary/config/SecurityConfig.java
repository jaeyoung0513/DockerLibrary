package com.example.jaeyounglibrary.config;

import com.example.jaeyounglibrary.components.CustomAccessDeniedHandler;
import com.example.jaeyounglibrary.components.CustomAuthenticationEntryPoint;
import com.example.jaeyounglibrary.jwt.JwtFilter;
import com.example.jaeyounglibrary.jwt.JwtUtil;
import com.example.jaeyounglibrary.jwt.LoginFilter;
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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 보호 비활성화 및 폼 로그인과 HTTP Basic 인증 비활성화
		http.csrf(csrf -> csrf.disable())
				.formLogin(formLogin -> formLogin.disable())
				.httpBasic(httpBasic -> httpBasic.disable())
				.authorizeHttpRequests(auth ->
						auth.requestMatchers("/", "/login", "/join", "/reissue", "/validate-loginId/**","/bookList").permitAll() // 특정 경로에 대해 모든 사용자 접근 허용
								.requestMatchers("/admin").hasRole("ADMIN") // /admin 경로에 대해 관리자 권한 필요
								.anyRequest().authenticated()); // 그 외의 모든 요청은 인증 필요
		
		// CORS 설정
		http.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of("http://localhost:3000")); // 클라이언트 도메인 설정
			config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메소드
			config.setAllowCredentials(true); // 쿠키 포함
			config.addExposedHeader("Authorization"); // 클라이언트에서 읽을 수 있는 헤더 추가
			config.addAllowedHeader("*"); // 모든 헤더 허용
			return config;
		}));
		
		http.logout(logout->logout.logoutUrl("/logout")
				.logoutSuccessHandler(logoutHandler())
				.addLogoutHandler((request, response, authentication)->{
					if(request.getSession()!=null){
						request.getSession().invalidate();
					}
				}).deleteCookies("JSESSIONID"));
		
		// 세션 설정 (Stateful 방식 사용하지 않음)
		http.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// 인증 필터 설정
		http.addFilterBefore(new JwtFilter(this.jwtUtil), LoginFilter.class);
		
		http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), this.jwtUtil),
				UsernamePasswordAuthenticationFilter.class);
		
		// 예외 처리 핸들러 설정
		http.exceptionHandling(exception -> {
			exception.authenticationEntryPoint(this.customAuthenticationEntryPoint);
			exception.accessDeniedHandler(this.customAccessDeniedHandler);
		});
		
		return http.build(); // 필터 체인 반환
	}
}