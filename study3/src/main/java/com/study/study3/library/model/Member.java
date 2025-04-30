package com.study.study3.library.model;

import com.study.study3.library.dto.MemberRequestRecord;
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

    public Member(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void addLoan(Loan loan) {
        if (this.loans == null) {
            this.loans = new ArrayList<>();
        }
        this.loans.add(loan);
    }

    public void updateMember(MemberRequestRecord request) {
        if (name != null && email != null && password != null && phoneNumber != null) {
            this.name = request.name();
            this.email = request.email();
            this.password = request.password();
            this.phoneNumber = request.phoneNumber();
        }
    }
}
