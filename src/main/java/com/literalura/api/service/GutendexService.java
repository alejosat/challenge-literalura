package com.literalura.api.service;

import com.literalura.api.model.Author;
import com.literalura.api.model.Book;
import com.literalura.api.model.Format;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GutendexService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://gutendex.com/books";

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("results")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

            for (Map<String, Object> result : results) {
                Book book = new Book();
                book.setId(Long.valueOf((Integer) result.get("id")));
                book.setTitle((String) result.get("title"));
                book.setMediaType((String) result.get("media_type"));
                book.setCopyright((boolean) result.get("copyright"));
                book.setDownloadCount((int) result.get("download_count"));

                // **Mapeo de Subjects**
                List<String> subjects = (List<String>) result.get("subjects");
                if (subjects != null) {
                    book.setSubjects(new ArrayList<>(subjects));
                }

                // **Mapeo de Bookshelves**
                List<String> bookshelves = (List<String>) result.get("bookshelves");
                if (bookshelves != null) {
                    book.setBookshelves(new ArrayList<>(bookshelves));
                }

                // **Mapeo de Languages**
                List<String> languages = (List<String>) result.get("languages");
                if (languages != null) {
                    book.setLanguages(new ArrayList<>(languages));
                }

                // **Mapeo de Autores**
                List<Map<String, Object>> authorsData = (List<Map<String, Object>>) result.get("authors");
                List<Author> authors = new ArrayList<>();
                if (authorsData != null) {
                    for (Map<String, Object> authorData : authorsData) {
                        Author author = new Author();
                        author.setName((String) authorData.get("name"));
                        author.setBirthYear((Integer) authorData.get("birth_year"));
                        author.setDeathYear((Integer) authorData.get("death_year"));
                        authors.add(author);
                    }
                }
                book.setAuthors(authors);

                // **Mapeo de Formatos**
                Map<String, String> formatsData = (Map<String, String>) result.get("formats");
                List<Format> formats = new ArrayList<>();
                if (formatsData != null) {
                    for (Map.Entry<String, String> entry : formatsData.entrySet()) {
                        Format format = new Format();
                        format.setType(entry.getKey());
                        format.setUrl(entry.getValue());
                        formats.add(format);
                    }
                }
                book.setFormats(formats);

                // Agregar el libro a la lista
                books.add(book);
            }
        }
        return books;
    }
}
