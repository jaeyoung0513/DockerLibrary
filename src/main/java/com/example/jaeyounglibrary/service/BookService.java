package com.example.jaeyounglibrary.service;

import com.example.jaeyounglibrary.data.dto.BookDTO;
import com.example.jaeyounglibrary.data.entity.BookEntity;
import com.example.jaeyounglibrary.data.entity.RentalEntity;
import com.example.jaeyounglibrary.data.entity.UserEntity;
import com.example.jaeyounglibrary.data.repository.BookRepository;
import com.example.jaeyounglibrary.data.repository.RentalRepository;
import com.example.jaeyounglibrary.data.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
	private final BookRepository bookRepository;
	private final RentalRepository rentalRepository;
	private final UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<BookDTO> getAllBook() {
		List<BookEntity> bookList = this.bookRepository.findAll();
		List<BookDTO> bookDTOList = new ArrayList<>();
		for (BookEntity book : bookList) {
			BookDTO bookDTO = BookDTO.builder()
					.bookId(book.getBookId())
					.title(book.getTitle())
					.author(book.getAuthor())
					.publisher(book.getPublisher())
					.category(book.getCategory())
					.description(book.getDescription())
					.availableCount(book.getAvailableCount())
					.image(book.getImage())
					.build();
			bookDTOList.add(bookDTO);
		}
		return bookDTOList;
	}
	
	@Transactional
	public BookDTO updateBook(Long bookId, String title, String description, String image) {
		BookEntity existingBook = this.bookRepository.findById(bookId)
				.orElseThrow(() -> new EntityNotFoundException("수정하려는 책이 존재하지 않습니다."));
		
		boolean isModified = false;
		
		if (!existingBook.getTitle().equals(title)) {
			existingBook.setTitle(title);
			isModified = true;
		}
		
		if (!existingBook.getDescription().equals(description)) {
			existingBook.setDescription(description);
			isModified = true;
		}
		
		if (!existingBook.getImage().equals(image)) {
			existingBook.setImage(image);
			isModified = true;
		}
		
		if (isModified) {
			this.bookRepository.save(existingBook);
		}
		
		return BookDTO.builder()
				.bookId(existingBook.getBookId())
				.title(existingBook.getTitle())
				.author(existingBook.getAuthor())
				.publisher(existingBook.getPublisher())
				.category(existingBook.getCategory())
				.description(existingBook.getDescription())
				.availableCount(existingBook.getAvailableCount())
				.image(existingBook.getImage())
				.build();
	}
	
	@Transactional
	public void deleteBook(Long bookId) {
		BookEntity deleteBook = this.bookRepository.findById(bookId)
				.orElseThrow(() -> new EntityNotFoundException("삭제하려는 책이 존재하지 않습니다."));
		this.bookRepository.delete(deleteBook);
		this.bookRepository.resetAutoIncrement();
	}
	
	@Transactional
	public void rentBook(Long bookId, String userId) {
		BookEntity book = bookRepository.findById(bookId)
				.orElseThrow(() -> new EntityNotFoundException("대여하려는 책이 존재하지 않습니다."));
		
		if (book.getAvailableCount() <= 0) {
			throw new IllegalStateException("대여 가능한 수량이 부족합니다.");
		}
		
		book.setAvailableCount(book.getAvailableCount() - 1);
		bookRepository.save(book);
		
		// 사용자 엔티티 조회
		UserEntity user = userRepository.findByLoginId(userId)
				.orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
		
		// 대여 정보 생성 및 저장
		RentalEntity rental = RentalEntity.builder()
				.book(book)
				.user(user) // Optional<UserEntity> 처리 완료
				.rentalDate(new Date())
				.dueDate(calculateDueDate()) // 대여 마감 날짜 계산
				.overdue(0)
				.build();
		
		rentalRepository.save(rental);
	}

	private Date calculateDueDate() {
		// 대여 후 자동 계산된 마감일 설정 (예: 14일 후)
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 14);
		return cal.getTime();
	}
}
