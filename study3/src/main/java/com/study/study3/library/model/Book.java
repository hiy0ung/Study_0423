package com.study.study3.library.model;

import com.study.study3.library.dto.BookRequest;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class Book {
    @Setter
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private List<String> categories = new ArrayList<>();
    private int publicationYear;
    @Setter
    private boolean available;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Book(Long id, String title, String author,
                String isbn, String description,
                List<String> categories, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.categories = categories;
        this.publicationYear = publicationYear;
        this.available = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Book(Long id, String title,
                String author, String isbn, String description,
                List<String> categories, int publicationYear, boolean available, LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.categories = categories;
        this.publicationYear = publicationYear;
        this.available = available;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateBook(BookRequest request) {
        if (title != null && author != null && isbn != null) {
            this.title = request.getTitle();
            this.author = request.getAuthor();
            this.isbn = request.getIsbn();
            this.description = request.getDescription();
            this.categories = request.getCategories();
            this.publicationYear = request.getPublicationYear();
        }
     }
}
