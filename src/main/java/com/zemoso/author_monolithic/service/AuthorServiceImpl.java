package com.zemoso.author_monolithic.service;

import com.zemoso.author_monolithic.dao.AuthorRepository;
import com.zemoso.author_monolithic.dto.AuthorDTO;
import com.zemoso.author_monolithic.entity.Author;
import com.zemoso.author_monolithic.exception.AuthorNotFoundException;
import com.zemoso.author_monolithic.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.zemoso.author_monolithic.constants.Constant.AUTHOR_NOT_FOUND;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        if (authorDTO == null || authorDTO.getName() == null || authorDTO.getName().isBlank()) {
            throw new IllegalArgumentException("AuthorDTO or Author name must not be null or blank");
        }

        Author author = new Author();
        author.setName(authorDTO.getName());
        Author savedAuthor = authorRepository.save(author);

        return AuthorMapper.toDTO(savedAuthor);
    }

    @Override
    public AuthorDTO findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        return authorRepository.findById(id)
                .map(AuthorMapper::toDTO)
                .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND + id));
    }

    @Override
    public Optional<Author> findAuthorEntityById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        return authorRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException(AUTHOR_NOT_FOUND + id);
        }

        authorRepository.deleteById(id);
    }
}
