package com.study.study3.library.dto;

import com.study.study3.library.model.Book;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean Validation
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
    private String isbn;

    @Size(max = 500, message = "Description cannot exceed 500 chracters")
    private String description;

    @Size(max = 5, message = "Maximum 5 categories allowed")
    private List<String> categories = new ArrayList<>();

    @NotNull(message = "Publication year is required")
    @Min(value = 1000, message = "Publication year must be at least 1000")
    private Integer publicationYear;

    public Book toEntity() {
        Book book = new Book();
        book.updateBook(this);
        book.setAvailable(true);
        return book;
    }
}
