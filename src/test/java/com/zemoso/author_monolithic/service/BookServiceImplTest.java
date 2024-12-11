package com.zemoso.author_monolithic.service;

import com.zemoso.author_monolithic.dao.BookRepository;
import com.zemoso.author_monolithic.dto.BookDTO;
import com.zemoso.author_monolithic.entity.Author;
import com.zemoso.author_monolithic.entity.Book;
import com.zemoso.author_monolithic.exception.AuthorNotFoundException;
import com.zemoso.author_monolithic.exception.BookNotFoundException;
import com.zemoso.author_monolithic.mapper.AuthorMapper;
import com.zemoso.author_monolithic.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorServiceImpl authorServiceImpl;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDTO bookDTO;
    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new Author();
        author.setId(1L);
        author.setName("John Doe");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor(author);

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor(AuthorMapper.toDTO(author));
    }

    @Test
    void testGetBooksByAuthorId_ValidId_ReturnsBooks() {
        when(bookRepository.findByAuthorId(1L)).thenReturn(Collections.singletonList(book));

        List<BookDTO> result = bookService.getBooksByAuthorId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findByAuthorId(1L);
    }

    @Test
    void testGetBooksByAuthorId_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.getBooksByAuthorId(-1L));
        verify(bookRepository, never()).findByAuthorId(anyLong());
    }

    @Test
    void testSave_ValidBookDTO_ReturnsSavedBookDTO() {
        when(authorServiceImpl.findAuthorEntityById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.save(bookDTO);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(authorServiceImpl, times(1)).findAuthorEntityById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testSave_InvalidBookDTO_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.save(null));
        verifyNoInteractions(authorServiceImpl, bookRepository);
    }

    @Test
    void testDeleteById_ValidId_DeletesBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        assertDoesNotThrow(() -> bookService.deleteById(1L));
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_InvalidId_ThrowsException() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteById(1L));
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetBooks_ValidPageable_ReturnsPage() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Book> page = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findAll(pageable)).thenReturn(page);

        Page<BookDTO> result = bookService.getBooks(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetBooksById_ValidId_ReturnsBookDTO() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<BookDTO> result = bookService.getBooksById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }
}
