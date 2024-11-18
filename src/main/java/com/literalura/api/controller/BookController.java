package com.literalura.api.controller;

import com.literalura.api.dto.AuthorDTO;
import com.literalura.api.dto.BookDTO;
import com.literalura.api.mapper.BookMapper;
import com.literalura.api.model.Author;
import com.literalura.api.model.Book;
import com.literalura.api.repository.AuthorRepository;
import com.literalura.api.repository.BookRepository;
import com.literalura.api.service.GutendexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "API para la gestión de libros y autores")
public class BookController {

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Operation(summary = "Importar libros desde el servicio Gutendex")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libros importados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al importar libros")
    })
    @GetMapping("/import")
    public ResponseEntity<String> importBooks() {
        List<Book> books = gutendexService.getBooks();
        bookRepository.saveAll(books);
        return ResponseEntity.ok("Books imported successfully");
    }

    @Operation(summary = "Obtener todos los libros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de libros", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<BookDTO>> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books.stream()
                .map(BookMapper::toBookDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Buscar libros por título")
    @Parameter(name = "title", description = "Título del libro", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libros encontrados", content = @Content(schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron libros")
    })
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return ResponseEntity.ok(books.stream()
                .map(BookMapper::toBookDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Obtener lista de autores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores", content = @Content(schema = @Schema(implementation = AuthorDTO.class)))
    })
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> getAuthorList() {
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok(authors.stream()
                .map(BookMapper::toAuthorDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Obtener autores vivos en un año específico")
    @Parameter(name = "year", description = "Año de referencia", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores vivos", content = @Content(schema = @Schema(implementation = AuthorDTO.class)))
    })
    @GetMapping("/authors/living")
    public ResponseEntity<List<AuthorDTO>> getLivingAuthorList(@RequestParam Integer year) {
        List<Author> livingAuthors = authorRepository.findLivingAuthorsInYear(year);
        return ResponseEntity.ok(livingAuthors.stream()
                .map(BookMapper::toAuthorDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Buscar libros por idioma")
    @Parameter(name = "language", description = "Idioma del libro", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de libros en el idioma especificado", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    })
    @GetMapping("/language")
    public ResponseEntity<List<BookDTO>> getBooksByLanguage(@RequestParam String language) {
        List<Book> books = bookRepository.findBooksByLanguage(language);
        return ResponseEntity.ok(books.stream()
                .map(BookMapper::toBookDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Obtener libro por ID")
    @Parameter(name = "id", description = "ID del libro", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles del libro", content = @Content(schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        return ResponseEntity.ok(BookMapper.toBookDTO(book));
    }

    @Operation(summary = "Eliminar un libro por ID")
    @Parameter(name = "id", description = "ID del libro", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        bookRepository.delete(book);
        return ResponseEntity.ok("Book deleted successfully");
    }

    @Operation(summary = "Buscar libros por tema")
    @Parameter(name = "subject", description = "Tema del libro", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de libros con el tema especificado", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    })
    @GetMapping("/subjects")
    public ResponseEntity<List<BookDTO>> getBooksBySubject(@RequestParam String subject) {
        List<Book> books = bookRepository.findBySubjectsContainingIgnoreCase(subject);
        return ResponseEntity.ok(books.stream()
                .map(BookMapper::toBookDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Obtener autor por ID")
    @Parameter(name = "id", description = "ID del autor", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles del autor", content = @Content(schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + id));
        return ResponseEntity.ok(BookMapper.toAuthorDTO(author));
    }
}
