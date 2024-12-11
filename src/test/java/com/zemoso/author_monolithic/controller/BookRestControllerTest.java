package com.zemoso.author_monolithic.controller;

import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.dto.BookDTO;
import com.zemoso.author_monolithic.entity.Author;
import com.zemoso.author_monolithic.exception.AuthorNotFoundException;
import com.zemoso.author_monolithic.exception.BookNotFoundException;
import com.zemoso.author_monolithic.service.AuthorServiceImpl;
import com.zemoso.author_monolithic.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRestControllerTest {

    @Mock
    private BookServiceImpl bookService;

    @Mock
    private AuthorServiceImpl authorServiceImpl;

    @InjectMocks
    private BookRestController bookRestController;

    private BookDTO bookDTO;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Author Name");

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Book Title");
        bookDTO.setAuthor(authorDTO);
    }

    @Test
    void testGetBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookDTO> booksPage = new PageImpl<>(Arrays.asList(bookDTO));
        when(bookService.getBooks(pageable)).thenReturn(booksPage);

        Page<BookDTO> result = bookRestController.getBooks(pageable);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(bookService, times(1)).getBooks(pageable);
    }

    @Test
    void testGetBookById() {
        when(bookService.getBooksById(1L)).thenReturn(Optional.of(bookDTO));

        BookDTO result = bookRestController.getBookById(1L);
        assertNotNull(result);
        assertEquals("Book Title", result.getTitle());
        verify(bookService, times(1)).getBooksById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookService.getBooksById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> bookRestController.getBookById(1L));
        assertEquals("Book not found in the database with ID: 1", exception.getMessage());
        verify(bookService, times(1)).getBooksById(1L);
    }

    @Test
    void testGetAuthorByBookId() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Author Name");

        when(bookService.getBooksById(1L)).thenReturn(Optional.of(bookDTO));
        when(authorServiceImpl.findAuthorEntityById(1L)).thenReturn(Optional.of(author));

        AuthorDTO result = bookRestController.getAuthorByBookId(1L);
        assertNotNull(result);
        assertEquals("Author Name", result.getName());
        verify(bookService, times(1)).getBooksById(1L);
        verify(authorServiceImpl, times(1)).findAuthorEntityById(1L);
    }

    @Test
    void testGetAuthorByBookId_BookNotFound() {
        when(bookService.getBooksById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> bookRestController.getAuthorByBookId(1L));
        assertEquals("Book not found", exception.getMessage());
        verify(bookService, times(1)).getBooksById(1L);
    }

    @Test
    void testGetAuthorByBookId_AuthorNotFound() {
        when(bookService.getBooksById(1L)).thenReturn(Optional.of(bookDTO));
        when(authorServiceImpl.findAuthorEntityById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AuthorNotFoundException.class, () -> bookRestController.getAuthorByBookId(1L));
        assertEquals("Author not found in the database with ID: 1", exception.getMessage());
        verify(bookService, times(1)).getBooksById(1L);
        verify(authorServiceImpl, times(1)).findAuthorEntityById(1L);
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookService).deleteById(1L);

        String result = bookRestController.deleteBook(1L);
        assertEquals("Book with ID 1 deleted successfully.", result);
        verify(bookService, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateBook() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Updated Author Name");

        AuthorDTO updatedAuthorDTO = new AuthorDTO();
        updatedAuthorDTO.setId(1L);
        updatedAuthorDTO.setName("Updated Author Name");

        BookDTO updatedBookDTO = new BookDTO();
        updatedBookDTO.setId(1L);
        updatedBookDTO.setTitle("Updated Title");
        updatedBookDTO.setAuthor(updatedAuthorDTO);

        when(bookService.getBooksById(1L)).thenReturn(Optional.of(bookDTO));
        when(authorServiceImpl.findAuthorEntityById(1L)).thenReturn(Optional.of(author));
        when(bookService.save(any(BookDTO.class))).thenReturn(updatedBookDTO);

        BookDTO result = bookRestController.updateBook(1L, updatedBookDTO);
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author Name", result.getAuthor().getName());
        verify(bookService, times(1)).getBooksById(1L);
        verify(authorServiceImpl, times(1)).findAuthorEntityById(1L);
        verify(bookService, times(1)).save(any(BookDTO.class));
    }

    @Test
    void testUpdateBook_BookNotFound() {
        BookDTO updatedBookDTO = new BookDTO();
        updatedBookDTO.setId(1L);
        updatedBookDTO.setTitle("Updated Title");

        when(bookService.getBooksById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> bookRestController.updateBook(1L, updatedBookDTO));
        assertEquals("Book not found in the database with ID: 1", exception.getMessage());
        verify(bookService, times(1)).getBooksById(1L);
    }

    @Test
    void testUpdateBook_AuthorNotFound() {
        AuthorDTO updatedAuthorDTO = new AuthorDTO();
        updatedAuthorDTO.setId(2L);
        updatedAuthorDTO.setName("Non-existent Author");

        BookDTO updatedBookDTO = new BookDTO();
        updatedBookDTO.setId(1L);
        updatedBookDTO.setTitle("Updated Title");
        updatedBookDTO.setAuthor(updatedAuthorDTO);

        when(bookService.getBooksById(1L)).thenReturn(Optional.of(bookDTO));
        when(authorServiceImpl.findAuthorEntityById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AuthorNotFoundException.class, () -> bookRestController.updateBook(1L, updatedBookDTO));
        assertEquals("Author not found in the database with ID: ", exception.getMessage());
        verify(bookService, times(1)).getBooksById(1L);
        verify(authorServiceImpl, times(1)).findAuthorEntityById(2L);
    }
}
