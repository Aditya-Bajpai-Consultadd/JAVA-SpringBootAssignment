package com.javaAssignment.Task2.api.repository;

import com.javaAssignment.Task2.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository  extends JpaRepository<Books, Long> {
    List<Books> findByTitleContainingIgnoreCase(String title);
    Optional<Books> findByTitle(String title);
}
