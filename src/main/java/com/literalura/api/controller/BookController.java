package com.literalura.api.controller;

import com.literalura.api.model.Book;
import com.literalura.api.repository.BookRepository;
import com.literalura.api.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/import")
    public ResponseEntity<String> importBooks() {
        List<Book> books = gutendexService.getBooks();
        bookRepository.saveAll(books);
        return ResponseEntity.ok("Books imported successfully");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }
}
