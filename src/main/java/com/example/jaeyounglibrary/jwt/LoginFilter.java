package com.example.jaeyounglibrary.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// 로그인 필터 클래스입니다. 인증 관리자와 JWT 유틸리티를 주입받습니다.
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
	// 인증 시도할 때 호출되는 메서드입니다.
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		String username = obtainUsername(req); // 요청에서 사용자명 추출
		String password = obtainPassword(req); // 요청에서 비밀번호 추출
		
		// 사용자명과 비밀번호로 인증 요청 토큰 생성
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, null);
		// 인증 관리자에게 인증 위임
		return this.authenticationManager.authenticate(authRequest);
	}
	
	// 인증 성공 시 호출되는 메서드입니다.
	@Override
	public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authentication) throws IOException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // 인증 성공한 사용자 정보
		String username = userDetails.getUsername();
		
		// 사용자 권한 정보 추출
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority grantedAuthority = iterator.next();
		String role = grantedAuthority.getAuthority();
		
		// 액세스 및 리프레시 토큰 생성
		String accessToken = this.jwtUtil.createJWT("access", username, role, 15 * 60 * 1000L);
		String refreshToken = this.jwtUtil.createJWT("refresh", username, role, 24 * 60 * 60 * 1000L);
		
		res.addHeader("Authorization", "Bearer " + accessToken);
		Cookie refreshCookie = createCookie("refresh", refreshToken);
		System.out.println("Refresh Cookie: " + refreshCookie.getValue()); // 디버깅 로그
		res.addCookie(refreshCookie);
		res.setCharacterEncoding("UTF-8");
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonmessage = objectMapper.writeValueAsString(role);
		res.getWriter().write(jsonmessage); // 성공 메시지 반환
	}
	
	@Override
	public void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException {
		Map<String, String> responseData = new HashMap<>();
		responseData.put("message", "계정정보가 틀립니다"); // 실패 메시지
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonmessage = objectMapper.writeValueAsString(responseData);
		
		// HTTP 상태를 401로 설정하고 JSON 메시지 반환
		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(jsonmessage);
	}
	
	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 60 * 24);
		cookie.setSecure(isSecureEnvironment()); // 배포 환경에서 true 설정
		return cookie;
	}
	
	// 환경에 따라 Secure 설정 여부 결정
	private boolean isSecureEnvironment() {
		return !System.getProperty("env", "local").equals("local");
	}
	
	
}