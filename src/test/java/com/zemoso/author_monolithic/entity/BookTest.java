package com.zemoso.author_monolithic.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookDefaultConstructor() {
        Book book = new Book();
        assertNotNull(book);
    }

    @Test
    void testBookConstructorWithTitleAndAuthor() {
        Author author = new Author(1L, "Author1");
        Book book = new Book("Book1", author);

        assertNotNull(book);
        assertEquals("Book1", book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testBookConstructorWithIdTitleAndAuthor() {
        Author author = new Author(1L, "Author1");
        Book book = new Book(1L, "Book1", author);

        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("Book1", book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testSettersAndGetters() {
        Book book = new Book();
        Author author = new Author(1L, "Author1");

        book.setId(1L);
        book.setTitle("Book1");
        book.setAuthor(author);

        assertEquals(1L, book.getId());
        assertEquals("Book1", book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testToString() {
        Author author = new Author(1L, "Author1");
        Book book = new Book(1L, "Book1", author);

        String expected = "Book{id=1, title='Book1', author=Author{id=1, name='Author1'}}";
        assertEquals(expected, book.toString());
    }
}
