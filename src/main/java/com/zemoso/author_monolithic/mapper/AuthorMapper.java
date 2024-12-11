package com.zemoso.author_monolithic.mapper;

import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.entity.Author;

public class AuthorMapper {

    public static AuthorDTO toDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        // Set other properties if needed
        return dto;
    }

    public static Author toEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setName(authorDTO.getName());
        // Set other properties if needed
        return author;
    }
}
