package com.study.study4.infrastructure.persistence.order.entity;

import com.study.study4.domain.order.OrderItem;
import com.study.study4.domain.product.ProductId;
import com.study.study4.domain.shared.Money;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    public static OrderItemEntity from(OrderItem orderItem) {
        return OrderItemEntity.builder()
                .id(orderItem.getId())
                .productId(orderItem.getId())
                .productName(orderItem.getProductName())
                .price(orderItem.getPrice().getAmount().intValue())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice().getAmount().intValue())
                .build();
    }

    public OrderItem toDomain() {
        return OrderItem.reconstitute(
                id,
                ProductId.of(productId),
                productName,
                Money.of(price),
                quantity,
                Money.of(totalPrice)
        );
    }
}
