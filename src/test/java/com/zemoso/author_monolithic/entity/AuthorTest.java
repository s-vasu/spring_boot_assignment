package com.zemoso.author_monolithic.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void testAuthorDefaultConstructor() {
        Author author = new Author();
        assertNotNull(author);
    }

    @Test
    void testAuthorConstructorWithIdAndName() {
        Author author = new Author(1L, "Author1");

        assertNotNull(author);
        assertEquals(1L, author.getId());
        assertEquals("Author1", author.getName());
    }

    @Test
    void testSettersAndGetters() {
        Author author = new Author();

        author.setId(1L);
        author.setName("Author1");
        List<Book> books = new ArrayList<>();
        author.setBooks(books);

        assertEquals(1L, author.getId());
        assertEquals("Author1", author.getName());
        assertEquals(books, author.getBooks());
    }

    @Test
    void testAddBook() {
        Author author = new Author(1L, "Author1");
        Book book = new Book("Book1", author);
        author.getBooks().add(book);

        assertNotNull(author.getBooks());
        assertEquals(1, author.getBooks().size());
        assertEquals(book, author.getBooks().get(0));
    }

    @Test
    void testToString() {
        Author author = new Author(1L, "Author1");

        String expected = "Author{id=1, name='Author1'}";
        assertEquals(expected, author.toString());
    }
}
