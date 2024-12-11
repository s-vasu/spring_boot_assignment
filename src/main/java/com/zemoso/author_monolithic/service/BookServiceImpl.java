package com.zemoso.author_monolithic.service;

import com.zemoso.author_monolithic.dao.BookRepository;
import com.zemoso.author_monolithic.dto.BookDTO;
import com.zemoso.author_monolithic.entity.Author;
import com.zemoso.author_monolithic.entity.Book;
import com.zemoso.author_monolithic.exception.AuthorNotFoundException;
import com.zemoso.author_monolithic.exception.BookNotFoundException;
import com.zemoso.author_monolithic.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorServiceImpl;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorServiceImpl authorServiceImpl) {
        this.bookRepository = bookRepository;
        this.authorServiceImpl = authorServiceImpl;
    }

    @Override
    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        if (authorId == null || authorId <= 0) {
            throw new IllegalArgumentException("Author ID must be a positive number");
        }

        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        if (bookDTO == null || bookDTO.getAuthor() == null || bookDTO.getAuthor().getId() == null) {
            throw new IllegalArgumentException("BookDTO or Author information must not be null");
        }

        // Convert DTO to entity
        Book book = BookMapper.toEntity(bookDTO);

        // Retrieve the Author entity using AuthorService
        Long authorId = bookDTO.getAuthor().getId();
        Author author = authorServiceImpl.findAuthorEntityById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with ID " + authorId + " not found."));

        book.setAuthor(author);

        // Save and convert back to DTO
        Book savedBook = bookRepository.save(book);
        return BookMapper.toDTO(savedBook);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Book ID must be a positive number");
        }

        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }

        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDTO> getBooks(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable parameter must not be null");
        }

        return bookRepository.findAll(pageable).map(BookMapper::toDTO);
    }

    @Override
    public Optional<BookDTO> getBooksById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Book ID must be a positive number");
        }

        return bookRepository.findById(id)
                .map(BookMapper::toDTO);
    }
}
