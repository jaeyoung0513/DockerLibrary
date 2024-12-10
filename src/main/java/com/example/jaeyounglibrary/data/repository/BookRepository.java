package com.example.jaeyounglibrary.data.repository;

import com.example.jaeyounglibrary.data.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
	@Modifying
	@Transactional
	@Query(value = "ALTER TABLE booktbl AUTO_INCREMENT = 1", nativeQuery = true)
	void resetAutoIncrement();
}
