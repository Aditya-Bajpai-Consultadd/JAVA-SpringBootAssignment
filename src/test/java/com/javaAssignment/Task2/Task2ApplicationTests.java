package com.javaAssignment.Task2;

import com.javaAssignment.Task2.api.repository.BookRepository;
import com.javaAssignment.Task2.entity.Books;
import com.javaAssignment.Task2.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Task2ApplicationTests {

	@Mock
	private BookRepository bookRepository; // Mock the repository

	@InjectMocks
	private BookService bookService; // Service being tested

	@Test
	public void testUpdateBookDetails_HappyPath() {
		// Arrange
		int bookId = 1;
		Books existingBook = new Books(bookId, "Aditya", "Aditya Bajpai", "Thriller", true);
		Books updates = new Books(null, "Updated Author", null, false);

		when(bookRepository.findById((long) bookId)).thenReturn(Optional.of(existingBook));
		when(bookRepository.save(any(Books.class))).thenAnswer(invocation -> {
			Books book = invocation.getArgument(0);
			return book;
		});


		Books updatedBook = bookService.updateBookDetails((long) bookId, updates);


		assertNotNull(updatedBook);
		assertEquals("Original Title", updatedBook.getTitle());
		assertEquals("Updated Author", updatedBook.getAuthor());
		assertEquals("Fiction", updatedBook.getGenre());
		assertFalse(updatedBook.isAvailable());
		verify(bookRepository).findById((long) bookId);
		verify(bookRepository).save(existingBook);
	}


}
