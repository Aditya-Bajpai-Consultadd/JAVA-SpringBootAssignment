package com.javaAssignment.Task2.api.controller;

import com.javaAssignment.Task2.entity.Books;
import com.javaAssignment.Task2.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class BookController {

private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/admin/books")
    public ResponseEntity<?> getAllBooks(){
        List<Books> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No books found in the library.");
        }
        return ResponseEntity.ok(books);
    }
    @PostMapping("/admin/books")
    public ResponseEntity<String> addBook(@RequestBody Books book) {
        if (bookService.doesBookExist(book.getTitle())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Book with title '" + book.getTitle() + "' already exists in the database.");
        }
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Book added successfully.");
    }
    @DeleteMapping("/admin/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBookById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Book with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(404).body("Book with ID " + id + " not found.");
        }
    }
    @GetMapping("/admin/books/search")
    public ResponseEntity<?> searchBooksByTitle(@RequestParam("title") String title) {
        List<Books> books =  bookService.searchBooksByTitle(title);
        if(books.isEmpty()) return ResponseEntity.status(400).body("No Such Book Found");
        return ResponseEntity.ok(books);
    }
    @PatchMapping("/admin/books/{id}")
    public ResponseEntity<?> updateBookDetails(@PathVariable Long id, @RequestBody Books updates) {
        try {
            Books updatedBook = bookService.updateBookDetails(id, updates);
            return ResponseEntity.ok(updatedBook);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating book details: " + e.getMessage());
        }
    }
}

