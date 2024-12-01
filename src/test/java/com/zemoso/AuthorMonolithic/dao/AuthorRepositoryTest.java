package com.zemoso.AuthorMonolithic.dao;

import com.zemoso.AuthorMonolithic.entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorRepositoryTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorRepositoryTest authorRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Author author = new Author(1L, "Author1");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(author, result.get());
        verify(authorRepository).findById(1L);
    }

    @Test
    void testSave() {
        Author author = new Author(1L, "Author1");
        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorRepository.save(author);

        assertNotNull(result);
        assertEquals("Author1", result.getName());
        verify(authorRepository).save(author);
    }

    @Test
    void testDeleteById() {
        doNothing().when(authorRepository).deleteById(1L);

        authorRepository.deleteById(1L);

        verify(authorRepository).deleteById(1L);
    }
}
