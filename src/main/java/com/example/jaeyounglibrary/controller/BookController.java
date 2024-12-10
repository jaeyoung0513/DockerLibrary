package com.example.jaeyounglibrary.controller;

import com.example.jaeyounglibrary.data.dto.BookDTO;
import com.example.jaeyounglibrary.data.entity.BookEntity;
import com.example.jaeyounglibrary.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;
	
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
	
//	@PostMapping(value = "/rentBook/{id}")
//	public ResponseEntity<String> rentBook(@PathVariable("id") Long bookId) {
//		try {
//			bookService.rentBook(bookId);
//			return ResponseEntity.status(HttpStatus.OK).body("책이 성공적으로 대여되었습니다.");
//		} catch (EntityNotFoundException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("책을 찾을 수 없습니다.");
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대여 과정에서 오류가 발생하였습니다.");
//		}
//	}
}
