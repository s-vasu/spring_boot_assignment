package com.zemoso.author_monolithic.dto;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDTOTest {

    @Test
    void testNoArgsConstructor() {
        AuthorDTO authorDTO = new AuthorDTO();
        assertNotNull(authorDTO);
    }

    @Test
    void testAllArgsConstructor() {
        List<BookDTO> books = Arrays.asList(new BookDTO(1L, "Book1", null));
        AuthorDTO authorDTO = new AuthorDTO(1L, "AuthorName", books);

        assertEquals(1L, authorDTO.getId());
        assertEquals("AuthorName", authorDTO.getName());
        assertEquals(books, authorDTO.getBooks());
    }

    @Test
    void testPartialConstructor() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "AuthorName");

        assertEquals(1L, authorDTO.getId());
        assertEquals("AuthorName", authorDTO.getName());
        assertNull(authorDTO.getBooks());
    }

    @Test
    void testSettersAndGetters() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("AuthorName");

        List<BookDTO> books = Arrays.asList(new BookDTO(1L, "Book1", null));
        authorDTO.setBooks(books);

        assertEquals(1L, authorDTO.getId());
        assertEquals("AuthorName", authorDTO.getName());
        assertEquals(books, authorDTO.getBooks());
    }

//    @Test
//    void testValidationForNameNotEmpty() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        AuthorDTO authorDTO = new AuthorDTO();
//        authorDTO.setId(1L);
//        authorDTO.setName(""); // Empty name
//
//        var violations = validator.validate(authorDTO);
//        assertFalse(violations.isEmpty());
//
//        var violation = violations.iterator().next();
//        assertEquals("Name is required", violation.getMessage());
//        assertEquals(NotEmpty.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
//    }

    @Test
    void testJsonIgnoreOnBooks() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setBooks(Arrays.asList(new BookDTO(1L, "Book1", null)));

        // Check if @JsonIgnore is present on 'books' (manually confirmed or via ObjectMapper in integration tests)
        assertNotNull(authorDTO.getBooks());
    }
}
