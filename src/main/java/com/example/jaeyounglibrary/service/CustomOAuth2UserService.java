package com.example.jaeyounglibrary.service;

import com.example.jaeyounglibrary.data.dto.*;
import com.example.jaeyounglibrary.data.dto.GoogleOAuth2Response;
import com.example.jaeyounglibrary.data.dto.NaverOAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
// DefaultOAuth2UserService를 확장하여 커스터마이징한 서비스 클래스
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	// OAuth2 사용자 정보를 로드하는 메서드 오버라이드
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// 기본 사용자 정보를 로드합니다.
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println(oAuth2User); // 로드된 사용자 정보를 콘솔에 출력
		
		// 등록된 클라이언트 ID를 가져옵니다.
		String registerId = userRequest.getClientRegistration().getRegistrationId();
		
		OAuth2Response oAuth2Response = null;
		
		// 등록된 ID에 따라 OAuth2Response 객체 생성
		if (registerId.equals("naver")) {
			oAuth2Response = new NaverOAuth2Response(oAuth2User.getAttributes()); // Naver의 경우
		} else if (registerId.equals("google")) {
			oAuth2Response = new GoogleOAuth2Response(oAuth2User.getAttributes()); // Google의 경우
		} else {
			return null; // 지원하지 않는 경우 null 반환
		}
		
		// 사용자 이름 생성: 제공자와 제공자 ID 결합
		String username = oAuth2Response.getProvider() + oAuth2Response.getProviderId();
		System.out.println("유저이름:" + username); // 생성된 사용자 이름을 출력
		
		// UserDTO 객체 생성 및 설정
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username); // 사용자 이름 설정
		userDTO.setLoginId(oAuth2Response.getName()); // 이름 설정
		userDTO.setRole("ROLE_USER"); // 기본 역할 설정
		
		// CustomOAuth2User 객체 반환
		return new CustomOAuth2User(userDTO);
	}
}
