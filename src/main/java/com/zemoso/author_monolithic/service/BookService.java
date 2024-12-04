package com.zemoso.author_monolithic.service;

import com.zemoso.author_monolithic.entity.Book;
import com.zemoso.author_monolithic.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }
}
