package com.study.study3.library.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    @Setter
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private List<Loan> loans = new ArrayList<>();
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public void addLoan(Loan loan) {
        if (this.loans == null) {
            this.loans = new ArrayList<>();
        }
        this.loans.add(loan);
    }

    public void updateMember() {

    }
}
