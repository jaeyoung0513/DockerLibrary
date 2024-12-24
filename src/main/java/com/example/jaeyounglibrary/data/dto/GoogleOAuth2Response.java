package com.example.jaeyounglibrary.data.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleOAuth2Response implements OAuth2Response{
	private final Map<String, Object> attributes;
	
	@Override
	public String getProvider() {
		return "google";
	}
	
	@Override
	public String getProviderId() {
		return this.attributes.get("sub").toString();
	}
	
	@Override
	public String getEmail() {
		return this.attributes.get("email").toString();
	}
	
	@Override
	public String getName() {
		return this.attributes.get("name").toString();
	}
}