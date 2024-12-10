package com.example.jaeyounglibrary.data.repository;

import com.example.jaeyounglibrary.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByLoginId(String loginId);
	Boolean existsByLoginId(String loginId);
}
