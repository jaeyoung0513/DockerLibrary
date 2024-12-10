package com.example.jaeyounglibrary.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rentalTBL")
public class RentalEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rentalId;
	
	private Date rentalDate;
	private Date returnDate;
	private Date dueDate;
	private Integer overdue;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "bookId")
	private BookEntity book;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "userId")
	private UserEntity user;
}
