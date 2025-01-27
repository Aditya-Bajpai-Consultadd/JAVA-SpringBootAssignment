package com.javaAssignment.Task2;

import com.javaAssignment.Task2.api.repository.BookRepository;
import com.javaAssignment.Task2.entity.Books;
import com.javaAssignment.Task2.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testUpdateBookDetails_HappyPath() {

        int bookId = 1;
        Books existingBook = new Books(bookId, "Aditya", "Aditya Bajpai", "Thriller", true);
        Books updates = new Books(null, "Updated Author", null, false);

        when(bookRepository.findById((long) bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Books.class))).thenAnswer(invocation -> {
            Books book = (Books) invocation.getArguments()[0];
            return book;
        });
        Books updatedBook = bookService.updateBookDetails((long) bookId, updates);
        assertNotNull(updatedBook);
        assertEquals("Aditya Bajpai", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
        assertEquals("Thriller", updatedBook.getGenre());
        assertFalse(updatedBook.isAvailable());
        verify(bookRepository).findById((long) bookId);
        verify(bookRepository).save(existingBook);
    }


    @Test
    void testUpdateBookDetails_UnHappyPath() {

        Long BookId = 1L;
        Books updates = new Books();
        when(bookRepository.findById(BookId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> bookService.updateBookDetails(BookId, updates));
        verify(bookRepository, never()).save(any(Books.class));
    }
}
