package com.study.study2.model;

import java.time.LocalDateTime;
import java.util.Objects;

public record MemberRequestRecord(
        String name,
        String email,
        int age
) {
    public MemberRequestRecord {
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(email, "Email must not be null");
    }

    public Member toEntity() {
        return new Member(null, name, email, age);
    }
}
