package com.literalura.api.mapper;

import com.literalura.api.dto.AuthorDTO;
import com.literalura.api.dto.BookDTO;
import com.literalura.api.dto.FormatDTO;
import com.literalura.api.model.Author;
import com.literalura.api.model.Book;
import com.literalura.api.model.Format;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static AuthorDTO toAuthorDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .birthYear(author.getBirthYear())
                .deathYear(author.getDeathYear())
                .build();
    }

    public static FormatDTO toFormatDTO(Format format) {
        return FormatDTO.builder()
                .id(format.getId())
                .type(format.getType())
                .url(format.getUrl())
                .build();
    }

    public static BookDTO toBookDTO(Book book) {
        List<AuthorDTO> authorDTOs = book.getAuthors().stream()
                .map(BookMapper::toAuthorDTO)
                .collect(Collectors.toList());

        List<FormatDTO> formatDTOs = book.getFormats().stream()
                .map(BookMapper::toFormatDTO)
                .collect(Collectors.toList());

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .mediaType(book.getMediaType())
                .copyright(book.isCopyright())
                .downloadCount(book.getDownloadCount())
                .subjects(book.getSubjects())
                .bookshelves(book.getBookshelves())
                .languages(book.getLanguages())
                .authors(authorDTOs)
                .formats(formatDTOs)
                .build();
    }

    public static Book toBook(BookDTO bookDTO) {
        List<Author> authors = bookDTO.getAuthors().stream()
                .map(BookMapper::toAuthor)
                .collect(Collectors.toList());

        List<Format> formats = bookDTO.getFormats().stream()
                .map(BookMapper::toFormat)
                .collect(Collectors.toList());

        return Book.builder()
                .id(bookDTO.getId())
                .title(bookDTO.getTitle())
                .mediaType(bookDTO.getMediaType())
                .copyright(bookDTO.isCopyright())
                .downloadCount(bookDTO.getDownloadCount())
                .subjects(bookDTO.getSubjects())
                .bookshelves(bookDTO.getBookshelves())
                .languages(bookDTO.getLanguages())
                .authors(authors)
                .formats(formats)
                .build();
    }

    public static Author toAuthor(AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .name(authorDTO.getName())
                .birthYear(authorDTO.getBirthYear())
                .deathYear(authorDTO.getDeathYear())
                .build();
    }

    public static Format toFormat(FormatDTO formatDTO) {
        return Format.builder()
                .id(formatDTO.getId())
                .type(formatDTO.getType())
                .url(formatDTO.getUrl())
                .build();
    }
}
