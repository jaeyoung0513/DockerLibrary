package com.example.jaeyounglibrary.data.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;


import java.util.ArrayList;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
	private final UserDTO userDTO;
	
	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add( new SimpleGrantedAuthority(this.userDTO.getRole()) );
		return authorities;
	}
	
	@Override
	public String getName() {
		return this.userDTO.getLoginId();
	}
	
	public String getUserName() {
		return this.userDTO.getUsername();
	}
}
