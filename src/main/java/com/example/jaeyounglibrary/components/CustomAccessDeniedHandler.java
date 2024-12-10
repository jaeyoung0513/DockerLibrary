package com.example.jaeyounglibrary.components;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 이 클래스는 AccessDeniedHandler 인터페이스를 구현하여,
// 사용자가 인가되지 않은 리소스에 접근하려 할 때의 대응 로직을 정의합니다.
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	// 사용자가 인가되지 않은 리소스에 접근을 시도할 때 호출됩니다.
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// HTTP 상태 코드를 403 Forbidden으로 설정합니다.
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		// 응답의 콘텐츠 타입을 JSON으로 설정합니다.
		response.setContentType("application/json");
		// 응답의 문자 인코딩을 UTF-8로 설정합니다.
		response.setCharacterEncoding("UTF-8");
		// 인가 실패 메시지를 클라이언트에 전송합니다.
		response.getWriter().write("현재 경로 요청은 권한이 없습니다.");
	}
}
