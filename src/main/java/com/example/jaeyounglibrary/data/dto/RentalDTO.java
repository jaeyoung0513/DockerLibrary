package com.example.jaeyounglibrary.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
	private Long rentalId;
	private LocalDate rentalDate;
	private LocalDate returnDate;
	private LocalDate dueDate;
	private Boolean isOverdue;  // Boolean으로 변경
	private Long bookId;
	private Long userId;
}

