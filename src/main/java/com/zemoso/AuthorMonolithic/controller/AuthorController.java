package com.zemoso.AuthorMonolithic.controller;

import com.zemoso.AuthorMonolithic.dao.AuthorRepository;
import com.zemoso.AuthorMonolithic.entity.Author;
import com.zemoso.AuthorMonolithic.entity.Book;
import com.zemoso.AuthorMonolithic.service.BookService;
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

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookService bookService;

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
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String editAuthorForm(@PathVariable Long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        model.addAttribute("author", author);
        return "author-edit";
    }

    @PostMapping("/edit/{id}")
    public String editAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        existingAuthor.setName(author.getName());
        authorRepository.save(existingAuthor);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }

    @GetMapping("/{authorId}/books")
    public String getBooksByAuthor(@PathVariable Long authorId, Model theModel) {
        // Log to confirm the method is being reached
        Author theAuthor = authorRepository.findById(authorId) .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        theModel.addAttribute("author",theAuthor);
        theModel.addAttribute("books", theAuthor.getBooks());
        return "books";
    }

    @PostMapping("/{authorId}/addBook")
    public String addBook(@PathVariable Long authorId, @RequestParam String title) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        bookService.save(book);
        return "redirect:/authors/" + authorId + "/books";
    }

    @PostConstruct
    public void init() {
        System.out.println("AuthorController initialized");
    }

}

