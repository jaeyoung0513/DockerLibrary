package com.example.jaeyounglibrary.service;
import com.example.jaeyounglibrary.data.entity.UserEntity;
import com.example.jaeyounglibrary.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User; // 추가된 import
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginService implements UserDetailsService {
	private final UserRepository entityRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = this.entityRepository.findByLoginId(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
		
		return new User(user.getLoginId(), user.getPassword(), grantedAuthorities);
	}
	
}
