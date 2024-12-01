package com.zemoso.AuthorMonolithic.service;

import com.zemoso.AuthorMonolithic.entity.Book;
import com.zemoso.AuthorMonolithic.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }
}
