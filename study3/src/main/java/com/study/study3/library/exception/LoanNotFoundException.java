package com.study.study3.library.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long id) {
        super("Loan not found with id: " + id);
    }
}
