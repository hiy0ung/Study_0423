package com.study.study3.library.dto;

import com.study.study3.library.model.Book;

import java.time.LocalDateTime;
import java.util.List;

public record BookResponseRecord(
        Long id,
        String title,
        String author,
        String isbn,
        String description,
        List<String> categories,
        int publicationYear,
        boolean available,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // 정적 팩토리 메서드 패턴
    public static BookResponseRecord from(Book book) {
        return new BookResponseRecord(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getDescription(),
                book.getCategories(),
                book.getPublicationYear(),
                book.isAvailable(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
