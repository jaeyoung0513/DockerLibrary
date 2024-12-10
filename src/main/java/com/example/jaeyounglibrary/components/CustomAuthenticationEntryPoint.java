package com.example.jaeyounglibrary.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 이 클래스는 AuthenticationEntryPoint 인터페이스를 구현하여,
// 인증이 필요한 경로에 비인증 사용자가 접근할 때의 대응 로직을 정의합니다.
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	// 비인증 사용자가 보호된 리소스에 접근을 시도할 때 호출됩니다.
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		// 응답 데이터 맵을 생성하여 에러 정보 및 메시지를 추가합니다.
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("error", "Unauthorized"); // 에러 유형
		responseData.put("message", "먼저 로그인하고 시도하세요"); // 사용자에게 보여질 실행 메시지
		
		// ObjectMapper를 사용하여 맵을 JSON 문자열로 변환합니다.
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonmessage = objectMapper.writeValueAsString(responseData);
		
		// 응답의 콘텐츠 타입을 JSON으로 설정합니다.
		response.setContentType("application/json");
		// 응답의 문자 인코딩을 UTF-8로 설정합니다.
		response.setCharacterEncoding("UTF-8");
		// HTTP 상태 코드를 401 Unauthorized로 설정합니다.
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		// JSON 메시지를 클라이언트에 전송합니다.
		response.getWriter().write(jsonmessage);
	}
}
