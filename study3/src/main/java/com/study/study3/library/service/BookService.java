package com.study.study3.library.service;

import com.study.study3.library.dto.BookRequest;
import com.study.study3.library.dto.BookResponseRecord;
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
     * 모든 도서 조회
     */
    //1
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //2
//    public List<BookResponseRecord> getAllBooks() {
//        return bookRepository.findAll().stream()
//                .map(BookResponseRecord::from)
//                .toList();
//    }

    /**
     * 특정 id 도서 조회
     */
    //1
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    //2
//    public BookResponseRecord getBookById(Long id) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new BookNotFoundException(id));
//        return BookResponseRecord.from(book);
//    }

    /**
     * 특정 isbn으로 도서 조회
     */
    //1
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    //2
//    public BookResponseRecord getBookByIsbn(String isbn) {
//        Book book = bookRepository.findByIsbn(isbn)
//                .orElseThrow(() -> new BookNotFoundException(isbn));
//        return BookResponseRecord.from(book);
//    }

    /**
     * 키워드로 도서 검색
     */
    //1
    public List<Book> getBooksByKeyword(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }

    //2
//    public List<BookResponseRecord> getBooksByKeyword(String keyword) {
//        return bookRepository.findByKeyword(keyword).stream()
//                .map(BookResponseRecord::from)
//                .toList();
//    }

    /**
     * 작가로 도서 검색
     */
    //1
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    //2
//    public List<BookResponseRecord> getBooksByAuthor(String author) {
//        return bookRepository.findByAuthor(author).stream()
//                .map(BookResponseRecord::from)
//                .toList();
//    }

    /**
     * 카테고리로 도서 검색
     */
    //1
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    //2
//    public List<BookResponseRecord> getBooksByCategory(String category) {
//        return bookRepository.findByCategory(category).stream()
//                .map(BookResponseRecord::from)
//                .toList();
//    }

    /**
     * 새로운 도서 생성
     */
    //1
    public Book createBook(BookRequest request) {
        // ISBN 중복 체크 (책의 id로 해도 됨)
        bookRepository.findByIsbn(request.getIsbn()).ifPresent(book -> {
            throw new IllegalArgumentException("Book with ISBN " + request.getIsbn() + " already exists");
        });

        Book book = request.toEntity();
        return bookRepository.save(book);
    }

    //2
//    public BookResponseRecord createBook(BookRequest request) {
//        bookRepository.findByIsbn(request.getIsbn()).ifPresent(book -> {
//            throw new IllegalArgumentException("Book with ISBN " + request.getIsbn() + " already exists");
//        });
//
//        Book book = bookRepository.save(request.toEntity());
//        return BookResponseRecord.from(book);
//    }

    /**
     * 도서 삭제
     */
    public boolean deleteBook(Long id) {
        return bookRepository.delete(id);
    }
}
