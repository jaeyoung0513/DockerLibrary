package com.example.jaeyounglibrary.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
	
	private final SecretKey secretKey;
	
	public JwtService(@Value("${authen.jwt.secret.key}") String secret) {
		if (secret == null || secret.isEmpty()) {
			throw new IllegalArgumentException("JWT 비밀 키가 설정되지 않았습니다.");
		}
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	// JWT 생성
	public String createToken(String category, String username, String role, long expireMs) {
		return Jwts.builder()
				.claim("category", category)
				.claim("username", username)
				.claim("role", role)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expireMs))
				.signWith(secretKey)
				.compact();
	}
	
	// JWT 파싱 및 검증
	public Claims parseToken(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			throw new IllegalArgumentException("JWT 토큰이 만료되었습니다.", e);
		} catch (JwtException e) {
			throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.", e);
		}
	}
	
	// JWT 만료 여부 확인
	public boolean isExpired(String token) {
		try {
			parseToken(token);
			return false;
		} catch (IllegalArgumentException e) {
			if (e.getCause() instanceof ExpiredJwtException) {
				return true;
			}
			throw e;
		}
	}
	
	// 카테고리 추출
	public String getCategory(String token) {
		return parseToken(token).get("category", String.class);
	}
	
	// 사용자명 추출
	public String getUsername(String token) {
		return parseToken(token).getSubject();
	}
	
	// 역할 추출
	public String getRole(String token) {
		return parseToken(token).get("role", String.class);
	}
}
