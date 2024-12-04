package com.zemoso.AuthorMonolithic.service;

import com.zemoso.AuthorMonolithic.entity.Book;
import com.zemoso.AuthorMonolithic.dao.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooksByAuthorId() {
        Long authorId = 1L;
        Book book1 = new Book("Book1", null);
        Book book2 = new Book("Book2", null);
        List<Book> books = List.of(book1, book2);

        when(bookRepository.findByAuthorId(authorId)).thenReturn(books);

        List<Book> result = bookService.getBooksByAuthorId(authorId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(book1, result.get(0));
        assertEquals(book2, result.get(1));
        verify(bookRepository).findByAuthorId(authorId);
    }

    @Test
    void testSaveBook() {
        Book book = new Book("Book1", null);

        when(bookRepository.save(book)).thenReturn(book);

        bookService.save(book);

        verify(bookRepository).save(book);
    }
}
