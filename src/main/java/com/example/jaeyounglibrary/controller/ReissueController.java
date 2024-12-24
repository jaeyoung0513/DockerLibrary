package com.example.jaeyounglibrary.controller;

import com.example.jaeyounglibrary.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ReissueController {
	
	private final JwtUtil jwtUtil;
	
	@PostMapping(value = "/reissue")
	public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
		// 리프레시 토큰 추출
		String refreshToken = getCookieValue(request, "refresh");
		System.out.println("Received refresh token: " + refreshToken);
		System.out.println("Request Cookies: " + Arrays.toString(request.getCookies()));
		
		
		if (refreshToken == null) {
			System.out.println("No refresh token found in cookies.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("refresh 토큰이 없습니다.");
		}
		
		try {
			jwtUtil.isExpired(refreshToken);
		} catch (ExpiredJwtException ex) {
			System.out.println("Refresh token expired.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh 토큰이 만료되었습니다.");
		}
		
		if (!"refresh".equals(jwtUtil.getCategory(refreshToken))) {
			System.out.println("Invalid refresh token category.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 refresh 토큰입니다.");
		}
		
		String username = jwtUtil.getUsername(refreshToken);
		String role = jwtUtil.getRole(refreshToken);
		System.out.println("Extracted username: " + username + ", role: " + role);
		
		String accessToken = jwtUtil.createJWT("access", username, role, 10 * 60 * 1000L);
		response.addHeader("Authorization", "Bearer " + accessToken);
		
		return ResponseEntity.ok("새로운 액세스 토큰이 발급되었습니다.");
	}
	
	
	/**
	 * 쿠키에서 특정 이름의 값을 가져옵니다.
	 */
	private String getCookieValue(HttpServletRequest request, String cookieName) {
		if (request.getCookies() == null) {
			return null;
		}
		Optional<Cookie> cookie = Arrays.stream(request.getCookies())
				.filter(c -> cookieName.equals(c.getName()))
				.findFirst();
		return cookie.map(Cookie::getValue).orElse(null);
	}
}
