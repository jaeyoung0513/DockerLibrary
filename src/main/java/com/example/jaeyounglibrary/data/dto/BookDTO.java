package com.example.jaeyounglibrary.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
	private Long bookId;
	private String title;
	private String author;
	private String publisher;
	private String category;
	private String description;
	private Integer availableCount;
	private String image;
}
