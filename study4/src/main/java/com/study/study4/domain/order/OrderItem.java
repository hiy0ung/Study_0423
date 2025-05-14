package com.study.study4.domain.order;

import com.study.study4.domain.product.ProductId;
import com.study.study4.domain.shared.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    private Long id;
    private ProductId productId;
    private String productName;
    private Money price;
    private int quantity;
    private Money totalPrice;

    @Builder
    public OrderItem(ProductId productId,
                     String productName, Money price,
                     int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price.multiply(quantity);

        validate();
    }

    private void validate() {
        // productId - null check
        // productName
        // price - null check, price.isLessThanOrEqual(Money.ZERO)
        // quantity - 0과 비교
    }

    public static OrderItem create(ProductId productId, String productName, Money price, int quantity) {
        return OrderItem.builder()
                .productId
                .build();
    }

    public static OrderItem reconstitute(Long id, ProductId productId, String productName,
                                        Money price, int quantity, Money totalPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.id = id;
        orderItem.productId = productId;
        orderItem.productName = productName;
        orderItem.price = price;
        orderItem.quantity = quantity;
        orderItem.totalPrice = totalPrice;
        return orderItem;
    }
}
