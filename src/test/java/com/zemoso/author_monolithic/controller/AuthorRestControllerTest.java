package com.zemoso.author_monolithic.controller;

import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.dto.BookDTO;
import com.zemoso.author_monolithic.service.AuthorServiceImpl;
import com.zemoso.author_monolithic.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorRestControllerTest {

    @Mock
    private AuthorServiceImpl authorService;

    @Mock
    private BookServiceImpl bookService;

    @InjectMocks
    private AuthorRestController authorRestController;

    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Test Author");
    }

    @Test
    void testListAuthors() {
        when(authorService.findAll()).thenReturn(Arrays.asList(authorDTO));

        ResponseEntity<List<AuthorDTO>> response = authorRestController.listAuthors();
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        verify(authorService, times(1)).findAll();
    }

    @Test
    void testCreateAuthor() {
        when(authorService.save(any(AuthorDTO.class))).thenReturn(authorDTO);

        ResponseEntity<AuthorDTO> response = authorRestController.createAuthor(authorDTO);
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test Author", response.getBody().getName());
        verify(authorService, times(1)).save(any(AuthorDTO.class));
    }

    @Test
    void testGetAuthorById() {
        when(authorService.findById(1L)).thenReturn(authorDTO);

        ResponseEntity<AuthorDTO> response = authorRestController.getAuthorById(1L);
        assertNotNull(response);
        assertEquals("Test Author", response.getBody().getName());
        verify(authorService, times(1)).findById(1L);
    }

    @Test
    void testDeleteAuthor() {
        doNothing().when(authorService).deleteById(1L);

        ResponseEntity<Void> response = authorRestController.deleteAuthor(1L);
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(authorService, times(1)).deleteById(1L);
    }
}
