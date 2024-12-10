package com.example.jaeyounglibrary.data.repository;

import com.example.jaeyounglibrary.data.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
}
