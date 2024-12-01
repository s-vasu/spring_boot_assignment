package com.zemoso.AuthorMonolithic.controller;

import com.zemoso.AuthorMonolithic.dao.AuthorRepository;
import com.zemoso.AuthorMonolithic.entity.Author;
import com.zemoso.AuthorMonolithic.entity.Book;
import com.zemoso.AuthorMonolithic.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookService bookService;

    @Mock
    private Model model;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAuthors() {
        List<Author> authors = Arrays.asList(new Author(1L, "Author1"), new Author(2L, "Author2"));
        when(authorRepository.findAll()).thenReturn(authors);

        String viewName = authorController.listAuthors(model);

        assertEquals("author-list", viewName);
        verify(model).addAttribute("authors", authors);
    }

    @Test
    void testCreateAuthorForm() {
        String viewName = authorController.createAuthorForm(model);

        assertEquals("author-create", viewName);
        verify(model).addAttribute(eq("author"), any(Author.class));
    }

    @Test
    void testCreateAuthor() {
        Author author = new Author();
        author.setName("New Author");

        String viewName = authorController.createAuthor(author);

        assertEquals("redirect:/authors", viewName);
        verify(authorRepository).save(author);
    }

    @Test
    void testEditAuthorForm() {
        Author author = new Author(1L, "Author1");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        String viewName = authorController.editAuthorForm(1L, model);

        assertEquals("author-edit", viewName);
        verify(model).addAttribute("author", author);
    }

    @Test
    void testEditAuthor() {
        Author existingAuthor = new Author(1L, "Existing Author");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));

        Author updatedAuthor = new Author();
        updatedAuthor.setName("Updated Author");

        String viewName = authorController.editAuthor(1L, updatedAuthor);

        assertEquals("redirect:/authors", viewName);
        verify(authorRepository).save(existingAuthor);
        assertEquals("Updated Author", existingAuthor.getName());
    }

    @Test
    void testDeleteAuthor() {
        String viewName = authorController.deleteAuthor(1L);

        assertEquals("redirect:/authors", viewName);
        verify(authorRepository).deleteById(1L);
    }

    @Test
    void testGetBooksByAuthor() {
        Author author = new Author(1L, "Author1");
        List<Book> books = Arrays.asList(new Book(1L, "Book1", author), new Book(2L, "Book2", author));
        author.setBooks(books);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookService.getBooksByAuthorId(1L)).thenReturn(books);

        String viewName = authorController.getBooksByAuthor(1L, model);

        assertEquals("books", viewName);
        verify(model).addAttribute("author", author);
        verify(model).addAttribute("books", books);
    }

    @Test
    void testAddBook() {
        Author author = new Author(1L, "Author1");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        String viewName = authorController.addBook(1L, "New Book");

        assertEquals("redirect:/authors/1/books", viewName);
        verify(bookService).save(any(Book.class));
    }

    @Test
    void testInit() {
        // Just confirm the init method doesn't throw exceptions
        assertDoesNotThrow(() -> authorController.init());
    }
}
