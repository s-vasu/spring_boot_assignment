package com.zemoso.author_monolithic.mapper;

import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.dto.BookDTO;
import com.zemoso.author_monolithic.entity.Book;
import com.zemoso.author_monolithic.entity.Author;

public class BookMapper {

    // Convert BookDTO to Book entity
    public static Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
        }
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());

        // Convert AuthorDTO to Author entity
        if (bookDTO.getAuthor() != null) {
            Author author = new Author();
            author.setId(bookDTO.getAuthor().getId());
            author.setName(bookDTO.getAuthor().getName());
            // Set other properties if needed
            book.setAuthor(author);
        }

        return book;
    }

    // Convert Book entity to BookDTO
    public static BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());

        // Convert Author entity to AuthorDTO
        if (book.getAuthor() != null) {
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(book.getAuthor().getId());
            authorDTO.setName(book.getAuthor().getName());
            // Set other properties if needed
            bookDTO.setAuthor(authorDTO);
        }

        return bookDTO;
    }
}
