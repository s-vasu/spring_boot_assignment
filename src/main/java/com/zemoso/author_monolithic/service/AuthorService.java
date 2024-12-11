package com.zemoso.author_monolithic.service;

import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    public List<AuthorDTO> findAll();
    public AuthorDTO save(AuthorDTO authorDTO);
    public AuthorDTO findById(Long id);
    public Optional<Author> findAuthorEntityById(Long id);
    public void deleteById(Long id);
}
