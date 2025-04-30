package com.study.study3.library.controller;

import com.study.study3.library.dto.BookRequest;
import com.study.study3.library.dto.BookResponseRecord;
import com.study.study3.library.model.Book;
import com.study.study3.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
