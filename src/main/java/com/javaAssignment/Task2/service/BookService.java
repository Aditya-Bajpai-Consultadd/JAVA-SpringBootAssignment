package com.javaAssignment.Task2.service;

import com.javaAssignment.Task2.api.repository.BookRepository;
import com.javaAssignment.Task2.entity.Books;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository= bookRepository;
    }
    public List<Books> getAllBooks(){
        return bookRepository.findAll();

    }
    public Optional<Books> getBookById(Long id) {
        return bookRepository.findById(id);
    }
public boolean doesBookExist(String title) {
        return bookRepository.findByTitle(title).isPresent();
    }
    public Books addBook(Books book) {
        return bookRepository.save(book);
    }
    public boolean deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Books> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    public Books updateBookDetails(Long id, Books updates) {
        Books existingBook = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));
        if (updates.getTitle() != null) {
            existingBook.setTitle(updates.getTitle());
        }
        if (updates.getAuthor() != null) {
            existingBook.setAuthor(updates.getAuthor());
        }
        if (updates.getGenre() != null) {
            existingBook.setGenre(updates.getGenre());
        }
        if (updates.isAvailable() != null) {
            existingBook.setAvailable(updates.isAvailable());
        }

        return bookRepository.save(existingBook);
    }
}
