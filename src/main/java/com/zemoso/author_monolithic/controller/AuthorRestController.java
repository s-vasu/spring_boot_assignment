package com.zemoso.author_monolithic.controller;

import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.dto.BookDTO;
import com.zemoso.author_monolithic.service.AuthorServiceImpl;
import com.zemoso.author_monolithic.service.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorRestController.class);

    private final AuthorServiceImpl authorServiceImpl;
    private final BookServiceImpl bookService;

    public AuthorRestController(AuthorServiceImpl authorServiceImpl, BookServiceImpl bookService) {
        this.authorServiceImpl = authorServiceImpl;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> listAuthors() {
        logger.info("Fetching all authors");
        return ResponseEntity.ok(authorServiceImpl.findAll());
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        logger.info("Creating new author: {}", authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorServiceImpl.save(authorDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        logger.info("Fetching author with ID: {}", id);
        return ResponseEntity.ok(authorServiceImpl.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> editAuthor(@PathVariable Long id, @RequestBody AuthorDTO updatedAuthorDTO) {
        logger.info("Updating author with ID: {}", id);
        AuthorDTO existingAuthor = authorServiceImpl.findById(id);
        existingAuthor.setName(updatedAuthorDTO.getName());
        return ResponseEntity.ok(authorServiceImpl.save(existingAuthor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        logger.info("Deleting author with ID: {}", id);
        authorServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long authorId) {
        logger.info("Fetching books for author with ID: {}", authorId);
        authorServiceImpl.findById(authorId); // Validate existence
        return ResponseEntity.ok(bookService.getBooksByAuthorId(authorId));
    }

    @PostMapping("/{authorId}/books")
    public ResponseEntity<BookDTO> addBook(@PathVariable Long authorId, @RequestBody BookDTO bookDTO) {
        logger.info("Adding new book for author with ID: {}", authorId);
        AuthorDTO author = authorServiceImpl.findById(authorId); // Validate existence
        bookDTO.setAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(bookDTO));
    }

    @DeleteMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long authorId, @PathVariable Long bookId) {
        logger.info("Deleting book with ID: {} for author with ID: {}", bookId, authorId);
        authorServiceImpl.findById(authorId); // Validate existence
        bookService.deleteById(bookId);
        return ResponseEntity.ok("Book with ID: " + bookId + " deleted!");
    }
}
