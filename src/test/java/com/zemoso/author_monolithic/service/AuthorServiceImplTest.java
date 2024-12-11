package com.zemoso.author_monolithic.service;

import com.zemoso.author_monolithic.dao.AuthorRepository;
import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.entity.Author;
import com.zemoso.author_monolithic.exception.AuthorNotFoundException;
import com.zemoso.author_monolithic.mapper.AuthorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Jane Doe");

        authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Jane Doe");
    }

    @Test
    void testFindAll_ReturnsListOfAuthorDTOs() {
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));

        List<AuthorDTO> result = authorService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testSave_ValidAuthorDTO_ReturnsSavedAuthorDTO() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO result = authorService.save(authorDTO);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testSave_NullOrInvalidAuthorDTO_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> authorService.save(null));

        AuthorDTO invalidDTO = new AuthorDTO();
        invalidDTO.setName(" ");
        assertThrows(IllegalArgumentException.class, () -> authorService.save(invalidDTO));
    }

    @Test
    void testFindById_ValidId_ReturnsAuthorDTO() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorDTO result = authorService.findById(1L);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_InvalidId_ThrowsException() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.findById(1L));
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById_ValidId_DeletesAuthor() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(authorRepository).deleteById(1L);

        assertDoesNotThrow(() -> authorService.deleteById(1L));
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_InvalidId_ThrowsException() {
        when(authorRepository.existsById(1L)).thenReturn(false);

        assertThrows(AuthorNotFoundException.class, () -> authorService.deleteById(1L));
        verify(authorRepository, never()).deleteById(anyLong());
    }
}

