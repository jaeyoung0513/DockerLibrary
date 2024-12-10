package com.example.jaeyounglibrary.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// JWT 인증 필터 클래스입니다. 각 요청마다 한 번씩 실행됩니다.
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	
	// 요청 필터링 메서드입니다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 요청 헤더에서 Authorization 값을 가져옵니다.
		String authToken = request.getHeader("Authorization");
		
		// 토큰이 존재하지 않거나 "Bearer "로 시작하지 않으면 그대로 필터 체인을 진행합니다.
		if (authToken == null || !authToken.startsWith("Bearer ")) {
			System.out.println("token null");
			filterChain.doFilter(request, response);
			return;
		}
		
		// "Bearer " 이후의 실제 토큰 값을 추출합니다.
		String token = authToken.split(" ")[1];
		
		try {
			// 토큰 만료 여부를 확인합니다.
			this.jwtUtil.isExpired(token);
		} catch (ExpiredJwtException ex) {
			// 만료된 토큰이면 에러 응답을 반환하고 필터링을 종료합니다.
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setCharacterEncoding("UTF-8");
			System.out.println("만료된 토큰");
			response.getWriter().write("만료된 토큰입니다");
			return;
		}
		
		// 토큰의 카테고리가 "access"인지 확인합니다.
		String category = this.jwtUtil.getCategory(token);
		if (!category.equals("access")) {
			// 카테고리가 올바르지 않으면 에러 응답을 반환하고 필터링을 종료합니다.
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setCharacterEncoding("UTF-8");
			System.out.println("허용되지 않은 토큰");
			response.getWriter().write("허용되지 않은 토큰입니다");
			return;
		}
		
		// 토큰에서 사용자명과 역할을 추출합니다.
		String username = this.jwtUtil.getUsername(token);
		String role = this.jwtUtil.getRole(token);
		
		// 권한 리스트를 생성하고 유저 정보로 Authentication 객체를 만듭니다.
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		
		User user = new User(username, "", authorities);
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
		
		// SecurityContextHolder에 인증 정보를 설정합니다.
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		// 다음 필터에 요청과 응답을 전달합니다.
		filterChain.doFilter(request, response);
	}
}