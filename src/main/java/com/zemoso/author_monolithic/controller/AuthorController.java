package com.zemoso.author_monolithic.controller;

import com.zemoso.author_monolithic.dao.AuthorRepository;
import com.zemoso.author_monolithic.entity.Author;
import com.zemoso.author_monolithic.entity.Book;
import com.zemoso.author_monolithic.service.BookService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private AuthorRepository authorRepository;

    private BookService bookService;

    private Author author;
    private String redirectAuthor = "redirect:/authors";
    private String notFoundMsg = "Author not found";

    @Autowired
    public AuthorController(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "author-list";
    }

    @GetMapping("/create")
    public String createAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "author-create";
    }

    @PostMapping("/create")
    public String createAuthor(@ModelAttribute Author author) {
        authorRepository.save(author);
        return redirectAuthor;
    }

    @GetMapping("/edit/{id}")
    public String editAuthorForm(@PathVariable Long id, Model model) {
        author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException(notFoundMsg));
        model.addAttribute("author", author);
        return "author-edit";
    }

    @PostMapping("/edit/{id}")
    public String editAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new RuntimeException(notFoundMsg));
        existingAuthor.setName(author.getName());
        authorRepository.save(existingAuthor);
        return redirectAuthor;
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
        return redirectAuthor;
    }

    @GetMapping("/{authorId}/books")
    public String getBooksByAuthor(@PathVariable Long authorId, Model theModel) {
        // Log to confirm the method is being reached
        author = authorRepository.findById(authorId) .orElseThrow(() -> new IllegalArgumentException(notFoundMsg));
        theModel.addAttribute("author",author);
        theModel.addAttribute("books", author.getBooks());
        return "books";
    }

    @PostMapping("/{authorId}/addBook")
    public String addBook(@PathVariable Long authorId, @RequestParam String title) {
        author = authorRepository.findById(authorId).orElseThrow();
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        bookService.save(book);
        return "redirect:/authors/" + authorId + "/books";
    }

    @PostConstruct
    public void init() {
        logger.info("AuthorController initialized");
    }

}

