package com.example.jaeyounglibrary.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 엔드포인트에 대해
				.allowedOrigins("http://localhost:3000") // React 애플리케이션 도메인
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용 HTTP 메서드
				.allowCredentials(true); // 인증 정보 포함 여부
	}
}