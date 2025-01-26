package com.javaAssignment.Task2.api.controller;

import com.javaAssignment.Task2.api.customException.BookAlreadyExistsException;
import com.javaAssignment.Task2.api.customException.NoBookFoundException;
import com.javaAssignment.Task2.entity.Books;
import com.javaAssignment.Task2.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/")
    public String func(HttpServletRequest httpServletRequest){
        return "Welcome to the website " + httpServletRequest.getSession().getId();
    }

    @GetMapping("/crsf-tk")
    public CsrfToken token(HttpServletRequest request){
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
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
        try {
            if (bookService.doesBookExist(book.getTitle())) {
                throw new BookAlreadyExistsException("Book with title '" + book.getTitle() + "' already exists in the database.");
            }
            bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Book added successfully.");
        } catch (BookAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
    @DeleteMapping("/admin/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try{
            Optional<Books> book = bookService.getBookById(id);
            if(book.isEmpty()){
                throw new NoBookFoundException("The Book with the id: "+ id + " not found.");
            }
            boolean isDeleted = bookService.deleteBookById(id);
            return ResponseEntity.ok("Book with ID " + id + " has been deleted.");
        }
        catch (NoBookFoundException ex)
        {
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
            Optional<Books> book = bookService.getBookById(id);
            if(book.isEmpty()){
                throw new NoBookFoundException("No Book found with id: "+ id);
            }
            Books updatedBook = bookService.updateBookDetails(id, updates);
            return ResponseEntity.ok(updatedBook);
        }catch (NoBookFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with id: " + id);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating book details: " + e.getMessage());
        }
    }
}

