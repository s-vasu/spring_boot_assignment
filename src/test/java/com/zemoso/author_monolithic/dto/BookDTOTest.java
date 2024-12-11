package com.zemoso.author_monolithic.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookDTOTest {

    @Test
    void testBookDTOConstructorWithParameters() {
        // Arrange
        Long id = 1L;
        String title = "Book Title";
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Author Name");

        // Act
        BookDTO bookDTO = new BookDTO(id, title, authorDTO);

        // Assert
        assertEquals(id, bookDTO.getId());
        assertEquals(title, bookDTO.getTitle());
        assertEquals(authorDTO, bookDTO.getAuthor());
    }

    @Test
    void testBookDTOConstructorWithoutParameters() {
        // Act
        BookDTO bookDTO = new BookDTO();

        // Assert
        assertNull(bookDTO.getId());
        assertNull(bookDTO.getTitle());
        assertNull(bookDTO.getAuthor());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        Long id = 2L;
        String title = "Another Book Title";
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(2L);
        authorDTO.setName("Another Author");

        // Act
        bookDTO.setId(id);
        bookDTO.setTitle(title);
        bookDTO.setAuthor(authorDTO);

        // Assert
        assertEquals(id, bookDTO.getId());
        assertEquals(title, bookDTO.getTitle());
        assertEquals(authorDTO, bookDTO.getAuthor());
    }

//    @Test
//    void testNotEmptyTitle() {
//        // Arrange
//        BookDTO bookDTO = new BookDTO();
//
//        // Act & Assert
//        assertThrows(javax.validation.ConstraintViolationException.class, () -> {
//            // Set title as empty to trigger validation error
//            bookDTO.setTitle("");
//        });
//    }

    @Test
    void testBookDTOConstructorWithCustomParams() {
        // Arrange
        String book1 = "Book1";
        Object o = new Object();

        // Act
        BookDTO bookDTO = new BookDTO(book1, o);

        // Assert
        assertNotNull(bookDTO);
        // Add further validation if needed, depending on your logic
    }
}
