package com.zemoso.author_monolithic.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
//    @NotEmpty(message = "Title is required")
    private String title;
    private AuthorDTO author; // Reference to AuthorDTO instead of direct Author entity

    public BookDTO(String book1, Object o) {
    }
}
