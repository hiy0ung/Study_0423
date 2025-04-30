package com.study.study3.library.repository;

import com.study.study3.library.model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LoanRepository {
    // 로거
    // 동시성 이슈를 고려한 ConcurrentHashMap 으로 구성한 loanStore
    // id 생성을 위한 AtomicLong 사용
    private static final Logger logger = LoggerFactory.getLogger(LoanRepository.class);
    private final Map<Long, Loan> loanStore = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    /**
     * 모든 대출 조회
     */
    public List<Loan> findAll() {
        return new ArrayList<>(loanStore.values());
    }

    /**
     * ID 로 특정 조회 (Optional)
     */
    public Optional<Loan> findById(Long id) {
        return Optional.ofNullable(loanStore.get(id));
    }

    /**
     * 회원 ID 로 대출 목록 조회
     */
    public List<Loan> findByMemberId(Long memberId) {
        return loanStore.values().stream()
                .filter(loan -> loan.getMember().getId().equals(memberId))
                .toList();
    }


    /**
     * 도서 ID 로 대출 목록 조회
     */
    public List<Loan> findByBookId(Long bookId) {
        return loanStore.values().stream()
                .filter(loan -> loan.getBook().getId().equals(bookId))
                .toList();
    }

    /**
     * 반납 여부로 대출 목록 필터링
     */
    public List<Loan> findByReturnStatus(boolean isReturned) {
        return loanStore.values().stream()
                .filter(loan -> loan.isReturned() == isReturned)
                .toList();
    }

    /**
     * 대출 저장(생성/수정)
     */
    public Loan save(Loan loan) {
        if(loan.getId() == null) {
            Long id = sequence.getAndIncrement();
            loan.setId(id);

            if (loan.getLoanDate() == null) {
                loan.setLoanDate(LocalDateTime.now());
            }

            if (loan.getDueDate() == null) {
                loan.setDueDate(loan.getLoanDate().plusDays(14));
            }

            logger.debug("Creating new loan with id: {}", id);
        }
        logger.debug("Updating loan with id: {}", loan.getId());
        loanStore.put(loan.getId(), loan);
        return loan;
    }

    /**
     * 대출 삭제
     */
    public boolean delete(Long id) {
        return loanStore.remove(id) != null;
    }

    /**
     * 도서 ID로 대출 삭제
     */
    public boolean deleteLoanByBookId(Long bookId) {
        return loanStore.values()
                .removeIf(loan -> loan.getBook().getId().equals(bookId));
    }

    /**
     * 회원 ID로 대출 삭제
     */
    public boolean deleteLoanByMemberId(Long memberId) {
        return loanStore.values()
                .removeIf(loan -> loan.getMember().getId().equals(memberId));
    }
}
