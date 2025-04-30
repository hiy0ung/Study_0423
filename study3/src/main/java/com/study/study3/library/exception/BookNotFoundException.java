package com.study.study3.library.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }

    public BookNotFoundException(String isbn) {
        super("Book not found with isbn: " + isbn);
    }


}
