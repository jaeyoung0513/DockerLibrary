package com.example.jaeyounglibrary.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentBookRequestDTO {
	private Long bookId;    // 대여할 책의 ID
}