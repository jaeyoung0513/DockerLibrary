package com.example.jaeyounglibrary.service;

import com.example.jaeyounglibrary.data.dto.UserDTO;
import com.example.jaeyounglibrary.data.entity.UserEntity;
import com.example.jaeyounglibrary.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public UserDTO searchUserById(String id) {
		Optional<UserEntity> userEntityOptional = userRepository.findByLoginId(id);
		return userEntityOptional.map(this::convertToDTO).orElse(null);
	}
	
	@Transactional
	public boolean join(UserDTO userDTO) {
		if (!existsByLoginId(userDTO.getLoginId())) {
			String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
			UserEntity newUser = UserEntity.builder()
					.loginId(userDTO.getLoginId())
					.password(encodedPassword)
					.username(userDTO.getUsername())
					.mobile(userDTO.getMobile())
					.overdueCount(0)
					.role("ROLE_USER")
					.currentRentalCount(0)
					.build();
			this.userRepository.save(newUser);
			return true;
		}
		return false;
	}
	
	@Transactional
	public UserDTO addUser(UserDTO user) {
		if (!existsByLoginId(user.getLoginId())) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			
			UserEntity userEntity = UserEntity.builder()
					.loginId(user.getLoginId())
					.username(user.getUsername())
					.password(encodedPassword)
					.mobile(user.getMobile())
					.role("ROLE_USER")
					.build();
			
			UserEntity savedEntity = userRepository.save(userEntity);
			return convertToDTO(savedEntity);
		}
		return null; // 이미 존재하는 경우 null 반환 (readability 향상)
	}
	
	private boolean existsByLoginId(String userId) {
		return userRepository.existsByLoginId(userId);
	}
	
	private List<UserDTO> convertToDTO(List<UserEntity> userEntities) {
		return userEntities.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}
	
	private UserDTO convertToDTO(UserEntity userEntity) {
		return UserDTO.builder()
				.loginId(userEntity.getLoginId())
				.username(userEntity.getUsername())
				.mobile(userEntity.getMobile())
				.role(userEntity.getRole())
				.build();
	}
	
	public boolean isLoginIdAvailable(String loginId) {
		return userRepository.existsByLoginId(loginId);
	}
}