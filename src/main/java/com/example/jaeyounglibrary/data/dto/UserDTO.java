package com.example.jaeyounglibrary.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String username;
	private String loginId;
	private String password;
	private String mobile;
	private String role;
	private Integer overdueCount;
	private Integer currentRentalCount;
}

