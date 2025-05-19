package com.study.bookstore.api.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotBlank(message = "Title is mandatory")
        String title,
        @NotBlank(message = "Author is mandatory")
        String author,
        @NotBlank(message = "ISBN is mandatory")
        String isbn,
        @NotNull(message = "Price is mandatory") // Integer 은 NotBlank가 될 수 없기 때문에
        Integer price,
        @NotNull(message = "Stock Quantity is mandatory")
        Integer stockQuantity
) {
}
