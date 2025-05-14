package com.study.bookstore.global.facade;

public interface PasswordEncoderFacade {
    String encode(String rawPassword);

    boolean matchs(String rawPassword, String encodePassword);
}
