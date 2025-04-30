package com.study.study3.library.service;

import com.study.study3.library.dto.BookRequest;
import com.study.study3.library.exception.BookNotFoundException;
import com.study.study3.library.model.Book;
import com.study.study3.library.repository.BookRepository;
import com.study.study3.library.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    /**
     * 해당 레포지토리에 있는 메서드 다 사용해서 만들어야됨 !
     */

    /**
     * 모든 도서 조회
     */

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * 특정 id 도서 조회
     */
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    /**
     * 특정 isbn으로 도서 조회
     */
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    /**
     * 키워드로 도서 검색
     */
    public List<Book> getBookByKeyword(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }

    /**
     * 작가로 도서 검색
     */
    public List<Book> getBookByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    /**
     * 카테고리로 도서 검색
     */
    public List<Book> getBookByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    /**
     * 새로운 도서 생성
     */
    public Book createBook(BookRequest request) {
        // ISBN 중복 체크 (책의 id로 해도 됨)
        bookRepository.findByIsbn(request.getIsbn()).ifPresent(book -> {
            throw new IllegalArgumentException("Book with ISBN " + request.getIsbn() + " already exists");
        });

        Book book = request.toEntity();
        return bookRepository.save(book);
    }

    /**
     * 기존 도서 수정
     */
}
