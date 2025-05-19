package com.study.bookstore.api.book.controller;

import com.study.bookstore.api.book.dto.request.BookRequest;
import com.study.bookstore.api.book.dto.request.BookWithCategoryRequest;
import com.study.bookstore.api.book.dto.response.BookResponse;
import com.study.bookstore.service.book.facade.BookFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "BOOK", description = "BOOK API")
@Validated
public class BookController {
    private final BookFacadeService bookFacadeService;

    @PostMapping
    @Operation(summary = "Save book", description = "Create a new book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@Valid @RequestBody BookRequest request) {
        return bookFacadeService.createBook(request);
    }

    @PostMapping("/category")
    @Operation(summary = "Save book", description = "Create a new book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBookWithCategory(@Valid @RequestBody BookWithCategoryRequest request) {
        return bookFacadeService.createBookWithCategory(request);
    }

    // update
    @PutMapping("/{bookId}")
    @Operation(summary = "update book", description = "Update a book")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse updateBook(@PathVariable Long bookId, @RequestParam int quantity) {
        return bookFacadeService.updateBook(bookId, quantity);
    }

    // getBookById
    @GetMapping("/{bookId}")
    @Operation(summary = "Get book", description = "Get a book by bookId")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getBookById(@PathVariable Long bookId) {
        return bookFacadeService.getBookById(bookId);
    }

    // deleteBook
    @DeleteMapping("/{bookId}")
    @Operation(summary = "Delete book", description = "Delete a book by bookId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long bookId) {
        bookFacadeService.deleteBook(bookId);
    }
}
