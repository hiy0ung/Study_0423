package com.study.study3.library.repository;

import com.study.study3.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import java.time.LocalDateTime;

@Repository
public class BookRepository {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    private final Map<Long, Book> bookStore = new ConcurrentHashMap<>();

    private final Map<String, Long> isbnIndex = new ConcurrentHashMap<>();

    private final AtomicLong sequence = new AtomicLong(1);

    // 모든 도서 조회
    public List<Book> findAll() {
        return new ArrayList<>(bookStore.values());
    }

    // id로 특정 도서 조회 null Optional
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookStore.get(id));
    }

    // isbn으로 특정 도서 조회 null
    public Optional<Book> findByIsbn(String isbn) {
        Long id = isbnIndex.get(isbn);
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(bookStore.get(id));
    }


    // 키워드로 도서 검색
    public List<Book> findByKeyword(String keyword) {
        logger.debug("Finding books by keyword: {}", keyword);

        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String lowerKeyword = keyword.toLowerCase();

        return bookStore.values().stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(lowerKeyword) ||
                        book.getAuthor().toLowerCase().contains(lowerKeyword) ||
                                (book.getDescription() != null &&
                                        book.getDescription().toLowerCase().contains(lowerKeyword)) ||
                        book.getCategories().stream().anyMatch(category ->
                                category.toLowerCase().contains(lowerKeyword)))
                .toList();
    }

    // 작가로 도서 검색 List
    public List<Book> findByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String lowerAuthor = author.toLowerCase();

        return bookStore.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(lowerAuthor))
                .toList();
    }

    // 카테고리로 도서 검색
    public List<Book> findByCategory(String category) {
        logger.debug("Finding books by category: {}", category);

        if (category == null || category.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String lowerCategory = category.toLowerCase();

        return bookStore.values().stream()
                .filter(book -> book.getCategories().stream()
                        .anyMatch(cat -> cat.toLowerCase().contains(lowerCategory)))
                .toList();
    }

    // 도서 저장 (생성/수정)
    public Book save(Book book) {
        if (book.getId() == null) {
            // 새 도서 생성
            Long id = sequence.getAndIncrement();
            book.setId(id);

            if (book.getCreatedAt() == null) {
                book.setCreatedAt(LocalDateTime.now());
            }

            logger.debug("Creating new book with id: {}" , id);

            // isbn 인덱스 추가
            if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                isbnIndex.put(book.getIsbn(), id);
            }
        } else {
            logger.debug("Updating book with id: {}", book.getId());

            Book existingBook = bookStore.get(book.getId());
            if (existingBook != null && !existingBook.getIsbn().equals(book.getIsbn())) {
                isbnIndex.remove(existingBook.getIsbn());
                isbnIndex.put(book.getIsbn(), book.getId());
            }
        }

        book.setUpdatedAt(LocalDateTime.now());
        bookStore.put(book.getId(), book);

        return book;
    }

    // 도서 삭제 boolean
    public boolean delete(Long id) {
        Book book = bookStore.get(id);
        // 연관된 데이터 삭제 정책 (CASCADE)
        if (book != null && book.getIsbn() != null) {
            isbnIndex.remove(book.getIsbn());
        }

        return bookStore.remove(id) != null;
    }
}
