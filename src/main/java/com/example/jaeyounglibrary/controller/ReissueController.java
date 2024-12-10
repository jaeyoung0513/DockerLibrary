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
import org.springframework.web.bind.annotation.RestController;

// JWT 토큰 갱신을 처리하는 컨트롤러 클래스입니다.
@RestController
@RequiredArgsConstructor
public class ReissueController {
	
	// JWT 유틸리티 클래스를 주입받습니다.
	private final JwtUtil jwtUtil;
	
	// '/reissue' 경로로 POST 요청이 왔을 때 실행됩니다.
	@PostMapping(value = "/reissue")
	public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = null;
		Cookie[] cookies = request.getCookies();
		
		// 모든 쿠키를 탐색하여 'refresh'라는 이름의 쿠키를 찾습니다.
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("refresh")) {
				refreshToken = cookie.getValue();
				break;
			}
		}
		
		// 리프레시 토큰이 없을 경우
		if (refreshToken == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("refresh 토큰 null");
		}
		
		try {
			// 리프레시 토큰이 만료되었는지 검사합니다.
			this.jwtUtil.isExpired(refreshToken);
		} catch (ExpiredJwtException ex) {
			// 만료된 경우 에러 메시지를 반환합니다.
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh 토큰 유효기간 만료");
		}
		
		// 토큰의 종류가 'refresh'인지 확인합니다.
		String category = this.jwtUtil.getCategory(refreshToken);
		if (!category.equals("refresh")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 refresh 토큰");
		}
		
		// 토큰에서 사용자명과 역할을 추출합니다.
		String username = this.jwtUtil.getUsername(refreshToken);
		String role = this.jwtUtil.getRole(refreshToken);
		
		// 새로운 액세스 토큰을 생성합니다.
		String accessToken = this.jwtUtil.CreateJWT("access", username, role, 5000L);
		
		// 응답 헤더에 새로운 액세스 토큰을 추가합니다.
		response.addHeader("Authorization", "Bearer " + accessToken);
		
		// 새로운 토큰 발급 성공 메시지를 반환합니다.
		return ResponseEntity.status(HttpStatus.OK).body("새 토큰 발급 성공");
	}
}