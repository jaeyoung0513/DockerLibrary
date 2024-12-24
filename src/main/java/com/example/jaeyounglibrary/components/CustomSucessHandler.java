package com.example.jaeyounglibrary.components;

import com.example.jaeyounglibrary.data.dto.CustomOAuth2User;
import com.example.jaeyounglibrary.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Component
public class CustomSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private static final String REDIRECT_URL = "http://www.jaeyoung.shop/oauth2/redirect"; // 리다이렉트 URL 상수
	private static final String TOKEN_NAME = "Authorization"; // 쿠키 이름 상수
	private static final long JWT_EXPIRATION_TIME = 10 * 60 * 1000L; // JWT 만료 시간 (10분)
	
	private final JwtUtil jwtUtil;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		String username = oAuth2User.getName();
		String role = extractRole(authentication.getAuthorities());
		
		// 액세스 및 리프레시 토큰 생성
		String accessToken = this.jwtUtil.createJWT("access", username, role, JWT_EXPIRATION_TIME);
		String refreshToken = this.jwtUtil.createJWT("refresh", username, role, 24 * 60 * 60 * 1000L);
		
		response.addHeader(TOKEN_NAME, "Bearer " + accessToken);
		Cookie refreshCookie = createCookie("refresh", refreshToken);
		System.out.println("Refresh Cookie: " + refreshCookie.getValue()); // 디버깅 로그
		response.addCookie(refreshCookie);
		response.sendRedirect(REDIRECT_URL + "?accessToken=" + accessToken + "&role=" + role);
	}
	
	/**
	 * 사용자 권한에서 첫 번째 역할을 추출
	 */
	private String extractRole(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
				.findFirst()
				.map(GrantedAuthority::getAuthority)
				.orElse("ROLE_USER"); // 기본 역할을 "ROLE_USER"로 설정
	}
	
	/**
	 * JWT를 포함한 쿠키 생성
	 */
	// 쿠키 생성 메서드
	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 60 * 24);
		cookie.setSecure(false);
		return cookie;
	}
}
