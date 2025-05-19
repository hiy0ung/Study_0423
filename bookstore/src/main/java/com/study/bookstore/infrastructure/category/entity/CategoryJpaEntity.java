package com.study.bookstore.infrastructure.category.entity;

import com.study.bookstore.domain.book.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class CategoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 도메인이랑?? 이름이 똑같아서 굳이 Column 이 안 붙어도 됨
    // 옵션도 없어서 안 써도 ㄱㅊ
    private String name;
    private String code;

    public static CategoryJpaEntity from(Category category) {
        return CategoryJpaEntity.builder()
                .name(category.getName())
                .code(category.getCode())
                .build();
    }

    // 세터 사용하지 않기 위해 대신 사용???
    public void updateName(String name) {
        this.name = name;
    }

    public void updateCode(String code) {
        this.code = code;
    }
}
