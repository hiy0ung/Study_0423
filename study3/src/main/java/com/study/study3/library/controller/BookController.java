package com.study.study3.library.controller;

import com.study.study3.library.dto.BookRequest;
import com.study.study3.library.dto.BookResponseRecord;
import com.study.study3.library.model.Book;
import com.study.study3.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    // 시간이 된다면 controller 도 작성...

    @PostMapping
    public ResponseEntity<BookResponseRecord> createBook(@Valid @RequestBody BookRequest request
    ) {
        Book book = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookResponseRecord.from(book));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseRecord>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookResponseRecord> response = books.stream()
                .map(BookResponseRecord::from)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseRecord> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(BookResponseRecord.from(book));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponseRecord> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(BookResponseRecord.from(book));
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<BookResponseRecord>> getBooksByKeyword(@RequestParam String keyword) {
        List<Book> books = bookService.getBooksByKeyword(keyword);
        List<BookResponseRecord> response = books.stream()
                .map(BookResponseRecord::from)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/author")
    public ResponseEntity<List<BookResponseRecord>> getBooksByAuthor(@RequestParam String author) {
        List<Book> books = bookService.getBooksByKeyword(author);
        List<BookResponseRecord> response = books.stream()
                .map(BookResponseRecord::from)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/category")
    public ResponseEntity<List<BookResponseRecord>> getBooksByCategory(@RequestParam String category) {
        List<Book> books = bookService.getBooksByKeyword(category);
        List<BookResponseRecord> response = books.stream()
                .map(BookResponseRecord::from)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteBook(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBook(id));
    }

}
