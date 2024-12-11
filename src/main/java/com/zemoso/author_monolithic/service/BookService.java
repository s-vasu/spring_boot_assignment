package com.zemoso.author_monolithic.service;

import com.zemoso.author_monolithic.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> getBooksByAuthorId(Long authorId);

    BookDTO save(BookDTO bookDTO);

    void deleteById(Long id);

    Page<BookDTO> getBooks(Pageable pageable);

    Optional<BookDTO> getBooksById(Long id);
}
