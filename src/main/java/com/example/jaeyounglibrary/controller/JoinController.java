package com.example.jaeyounglibrary.controller;

import com.example.jaeyounglibrary.data.dto.UserDTO;
import com.example.jaeyounglibrary.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class JoinController {
	private final UserService userService;
	
	@PostMapping(value = "/join")
	public ResponseEntity<String> addUserInfo(@Valid @RequestBody UserDTO user) {
		if (user.getLoginId() == null || user.getLoginId().isBlank()) {
			return ResponseEntity.badRequest().body("사용자 ID는 필수 입력 사항입니다.");
		}
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			return ResponseEntity.badRequest().body("비밀번호는 필수 입력 사항입니다.");
		}
		
		this.userService.join(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입이 완료되었습니다.");
	}
	@GetMapping("/validate-loginId/{loginId}")
	public ResponseEntity<Boolean> validateUserId(@PathVariable("loginId") String loginId) {
		boolean isAvailable = userService.isLoginIdAvailable(loginId);
		return ResponseEntity.status(HttpStatus.OK).body(isAvailable);
	}
}
