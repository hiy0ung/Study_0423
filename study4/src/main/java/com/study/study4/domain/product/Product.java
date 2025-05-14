package com.study.study4.domain.product;

import com.study.study4.domain.shared.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    private ProductId id;
    private String name;
    private Money price;
    private String description;
    private ProductCategory category;
    private int stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public static Product create(String name, Money price, String description,
                   ProductCategory category, int stockQuantity) {
        Product product = new Product();
        product.name = name;
        product.price = price;
        product.description = description;
        product.category = category;
        product.stockQuantity = stockQuantity;
        product.createdAt = LocalDateTime.now();
        product.updatedAt = LocalDateTime.now();

        product.validate();

        return product;
    }

    private void validate() {

    }

    public static Product reconstitute(ProductId id, String name, Money price, String description,
                                       ProductCategory category, int stockQuantity,
                                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        Product product = new Product();
        product.id = id;
        product.name = name;
        product.price = price;
        product.description = description;
        product.category = category;
        product.stockQuantity = stockQuantity;
        product.createdAt = createdAt;
        product.updatedAt = updatedAt;

        return product;
    }

    public void decreasesStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("감소량은 양수여야 합니다.");
        }

        if (this.stockQuantity < quantity) {
            throw new IllegalArgumentException("재고가 없습니다.");
        }

        this.stockQuantity -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("증가량은 양수여야 합니다.");
        }
        this.stockQuantity += quantity;
    }
}
