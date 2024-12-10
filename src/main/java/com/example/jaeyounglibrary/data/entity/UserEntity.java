package com.example.jaeyounglibrary.data.entity;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "userTBL")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String username;
	
	@Column(unique=true, nullable=false)
	private String loginId;
	
	@Column(nullable=false)
	private String password;
	
	private String mobile;
	
	private String role;
	
	private Integer overdueCount;
	
	private Integer currentRentalCount;
	
	public UserEntity orElseThrow(Object o) {
		return this;
	}
}
