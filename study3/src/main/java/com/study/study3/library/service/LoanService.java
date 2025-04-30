package com.study.study3.library.service;

import com.study.study3.library.exception.LoanNotFoundException;
import com.study.study3.library.model.Book;
import com.study.study3.library.model.Loan;
import com.study.study3.library.model.Member;
import com.study.study3.library.repository.BookRepository;
import com.study.study3.library.repository.LoanRepository;
import com.study.study3.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    // 3가지의 레파지토리를 다 가져와야됨
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    /**
     * 모든 대출 조회
     */
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    /**
     * 특정 ID로 조회
     */
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }

    /**
     * 회원 ID로 대출 목록 조히
     */
    public List<Loan> getLoanByMemberId(Long memberId) {
        return loanRepository.findByMemberId(memberId);
    }

    /**
     * 도서 ID 로 대출 목록 조히
     */
    public List<Loan> getLoanByBookId(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }

    /**
     * 반납 여부로 대출 목록 필터링
     */
    public List<Loan> getLoanByReturnStatus(boolean isReturned) {
        return loanRepository.findByReturnStatus(isReturned);
    }

    /**
     * 대출 저장(생성/수정)
     */
    public Loan createLoan(Long bookId, Long memberId) {
        if (bookRepository.findById(bookId).isEmpty()) {
            throw new IllegalArgumentException("Book not found with id.");
        }

        if (memberRepository.findById(memberId).isEmpty()) {
            throw new IllegalArgumentException("Member not found with id.");
        }

        Book book = bookRepository.findById(bookId).get();
        Member member = memberRepository.findById(memberId).get();

        if (!book.isAvailable()) {
            throw new IllegalStateException("This book is already borrowed.");
        }

        Loan loan = new Loan(book, member);
        return loanRepository.save(loan);
    }

    /**
     * 대출 삭제
     */
    public boolean deleteLoan(Long id) {
        return loanRepository.delete(id);
    }

    /**
     * 도서 ID로 대출 삭제
     */
    public boolean deleteLoanByBookId(Long bookId) {
        return loanRepository.deleteLoanByBookId(bookId);
    }

    /**
     * 회원 ID로 대출 삭제
     */
    public boolean deleteLoanByMemberId(Long memberId) {
        return loanRepository.deleteLoanByMemberId(memberId);
    }

    /**
     * 대출 기간 연징 (수정)
     */
    public Loan extendLoan(Long id) {
        Loan loan = getLoanById(id);

        if (loan.isReturned()) {
            throw new IllegalStateException("Cannot extend the loan for a returned book.");
        }

        loan.setLoanDate(loan.getDueDate().plusDays(7));
        return loanRepository.save(loan);
    }
}
