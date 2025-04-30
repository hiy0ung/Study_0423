package com.study.study3.library.dto;

import com.study.study3.library.model.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberRequestRecord(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "name is required")
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        String email,

        @NotBlank(message = "name is required")
        @Pattern(regexp = "^(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
                message = "Password must be 8 to 20 characters long and include at least one special character.")
        String password,

        @NotBlank(message = "name is required")
        @Pattern(regexp = "^\\d{10,11}$",
                message = "Phone number must contain 10 or 11 digits with no dashes or spaces.")
        String phoneNumber
) {
    public Member toEntity() {
        Member member = new Member();
        member.updateMember(this);
        return member;
    }
}
