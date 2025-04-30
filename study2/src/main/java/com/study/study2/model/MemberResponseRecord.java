package com.study.study2.model;

import java.time.LocalDateTime;

public record MemberResponseRecord(
        Long id,
        String name,
        String email,
        int age,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {

    public static MemberResponseRecord from(Member member) {
        return new MemberResponseRecord(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getAge(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}
