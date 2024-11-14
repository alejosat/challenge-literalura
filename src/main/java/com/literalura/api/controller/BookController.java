package com.literalura.api.controller;

import com.literalura.api.model.Author;
import com.literalura.api.model.Book;
import com.literalura.api.repository.AuthorRepository;
import com.literalura.api.repository.BookRepository;
import com.literalura.api.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/import")
    public ResponseEntity<String> importBooks() {
        List<Book> books = gutendexService.getBooks();
        bookRepository.saveAll(books);
        return ResponseEntity.ok("Books imported successfully");
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAuthorList() {
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/authors/living")
    public ResponseEntity<List<Author>> getLivingAuthorList(@RequestParam int year) {
        List<Author> livingAuthors = authorRepository.findLivingAuthorsInYear(year);
        return ResponseEntity.ok(livingAuthors);
    }

    @GetMapping("/language")
    public ResponseEntity<List<Book>> getBooksByLanguage(@RequestParam String language) {
        List<Book> booksByLanguage = bookRepository.findBooksByLanguage(language);
        return ResponseEntity.ok(booksByLanguage);
    }
}
