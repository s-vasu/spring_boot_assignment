package com.zemoso.author_monolithic.controller;

import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.dto.BookDTO;
import com.zemoso.author_monolithic.entity.Author;
import com.zemoso.author_monolithic.exception.AuthorNotFoundException;
import com.zemoso.author_monolithic.exception.BookNotFoundException;
import com.zemoso.author_monolithic.service.AuthorServiceImpl;
import com.zemoso.author_monolithic.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.zemoso.author_monolithic.constants.Constant.AUTHOR_NOT_FOUND;
import static com.zemoso.author_monolithic.constants.Constant.BOOK_NOT_FOUND;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookServiceImpl bookService;
    private final AuthorServiceImpl authorServiceImpl;

    @Autowired
    public BookRestController(BookServiceImpl bookService, AuthorServiceImpl authorServiceImpl) {
        this.bookService = bookService;
        this.authorServiceImpl = authorServiceImpl;
    }

    @GetMapping
    public Page<BookDTO> getBooks(Pageable pageable) {
        return bookService.getBooks(pageable);
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        return bookService.getBooksById(id)
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND+id));
    }

    @GetMapping("/{bookId}/author")
    public AuthorDTO getAuthorByBookId(@PathVariable Long bookId) {
        Optional<BookDTO> bookDTOOpt = bookService.getBooksById(bookId);

        if (bookDTOOpt.isPresent()) {
            BookDTO bookDTO = bookDTOOpt.get();
            Long authorId = bookDTO.getAuthor().getId();
            Author author = authorServiceImpl.findAuthorEntityById(authorId)
                    .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND+authorId));

            // Convert the Author entity to AuthorDTO
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(author.getId());
            authorDTO.setName(author.getName());
            // Map other properties as needed

            return authorDTO;
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "Book with ID " + id + " deleted successfully.";
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        // Fetch the existing book
        BookDTO existingBook = bookService.getBooksById(id)
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND+id));

        // Update the book's title
        existingBook.setTitle(updatedBookDTO.getTitle());

        // Update the author if provided
        if (updatedBookDTO.getAuthor() != null && updatedBookDTO.getAuthor().getId() != null) {
            Author author = authorServiceImpl.findAuthorEntityById(updatedBookDTO.getAuthor().getId())
                    .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND));
            AuthorDTO updatedAuthorDTO = new AuthorDTO();
            updatedAuthorDTO.setId(author.getId());
            updatedAuthorDTO.setName(author.getName());
            // Map other properties if needed
            existingBook.setAuthor(updatedAuthorDTO);
        }

        // Save the updated book
        return bookService.save(existingBook);
    }
}
