package com.example.jaeyounglibrary.controller;

import com.example.jaeyounglibrary.data.dto.BookDTO;
import com.example.jaeyounglibrary.data.dto.RentBookRequestDTO;
import com.example.jaeyounglibrary.service.BookService;
import com.example.jaeyounglibrary.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class BookController {
	private final BookService bookService;
	private final JwtService jwtService;
	
	@GetMapping(value = "/bookList")
	public ResponseEntity<List<BookDTO>> getProductList() {
		List<BookDTO> bookDTOList = this.bookService.getAllBook();
		return ResponseEntity.status(HttpStatus.OK).body(bookDTOList);
	}
	
	@PutMapping(value = "/editBook")
	public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO book) {
		try {
			BookDTO updatedBookDTO = this.bookService.updateBook(book.getBookId(), book.getTitle(), book.getDescription(), book.getImage());
			return ResponseEntity.status(HttpStatus.OK).body(updatedBookDTO);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@DeleteMapping(value = "/deleteBook/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") Long bookId) {
		try {
			this.bookService.deleteBook(bookId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping(value = "/rentBook")
	public ResponseEntity<String> rentBook(
			@RequestHeader("Authorization") String token,
			@RequestBody RentBookRequestDTO rentRequest
	) {
		try {
			System.out.println("Authorization Header: " + token); // 디버깅 로그 추가
			
			String jwtToken = token.replace("Bearer ", "");
			String userId = jwtService.getUsername(jwtToken);
			
			System.out.println("Extracted User ID: " + userId); // 사용자 ID 확인
			
			bookService.rentBook(rentRequest.getBookId(), userId);
			return ResponseEntity.status(HttpStatus.OK).body("책이 성공적으로 대여되었습니다.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("책을 찾을 수 없습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대여 과정에서 오류가 발생하였습니다.");
		}
	}
	
}
