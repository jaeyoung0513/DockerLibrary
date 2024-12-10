package com.example.jaeyounglibrary.data.entity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookTBL")
public class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;
	
	private String title;
	private String author;
	private String publisher;
	private String category;
	private String description;
	private Integer availableCount;
	private String image;
	
}
