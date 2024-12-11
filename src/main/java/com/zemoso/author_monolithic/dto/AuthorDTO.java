package com.zemoso.author_monolithic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;
    @JsonIgnore
    private List<BookDTO> books; // This should be a DTO list to avoid direct entity use

    public AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
