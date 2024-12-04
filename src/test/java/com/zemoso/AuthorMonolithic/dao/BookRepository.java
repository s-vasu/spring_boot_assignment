package com.zemoso.AuthorMonolithic.dao;

import com.zemoso.AuthorMonolithic.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookRepositoryTest bookRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByAuthorId() {
        Long authorId = 1L;
        Book book1 = new Book("Book1", null);
        Book book2 = new Book("Book2", null);
        List<Book> books = List.of(book1, book2);

        when(bookRepository.findByAuthorId(authorId)).thenReturn(books);

        List<Book> result = bookRepository.findByAuthorId(authorId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(book1, result.get(0));
        assertEquals(book2, result.get(1));
        verify(bookRepository).findByAuthorId(authorId);
    }

    @Test
    void testSave() {
        Book book = new Book("Book1", null);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookRepository.save(book);

        assertNotNull(result);
        assertEquals("Book1", result.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void testDeleteById() {
        doNothing().when(bookRepository).deleteById(1L);

        bookRepository.deleteById(1L);

        verify(bookRepository).deleteById(1L);
    }
}

