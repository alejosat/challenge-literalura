package com.literalura.api.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String mediaType;
    private boolean copyright;
    private int downloadCount;
    private List<String> subjects;
    private List<String> bookshelves;
    private List<String> languages;
    private List<AuthorDTO> authors;
    private List<FormatDTO> formats;
}