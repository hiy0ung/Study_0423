package com.study.study4.infrastructure.persistence.order.entity;

import com.study.study4.domain.order.Order;
import com.study.study4.domain.order.OrderId;
import com.study.study4.domain.order.OrderItem;
import com.study.study4.domain.order.OrderStatus;
import com.study.study4.domain.shared.Money;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "orderEntity")
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.orderedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems.clear();
        if (orderItems != null) {
            orderItems.forEach(this::addOrderItem);
        }
    }

    public void addOrderItem(OrderItemEntity itemEntity) {
        orderItems.add(itemEntity);
        itemEntity.setOrderEntity(this);
    }

    public static OrderEntity from(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId() != null ? order.getId().getValue() : null)
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .shippingAddress(order.getShippingAddress())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount().getAmount().intValue()) // BigDecimal 을 Integer로 변환 해야됨
                .orderedAt(order.getOrderedAt())
                .updatedAt(order.getUpdatedAt())
                .build();

        List<OrderItemEntity> itemEntities = order.getOrderItems().stream()
                .map(OrderItemEntity::from)
                .toList();

        orderEntity.setOrderItems(itemEntities);

        return orderEntity;
    }

    public Order toDomain() {
        // 주문 항목
        List<OrderItem> domainOrderItems = orderItems.stream()
                .map(OrderItemEntity::toDomain)
                .toList();
        return Order.reconstitute(
                OrderId.of(id),
                customerName,
                customerEmail,
                shippingAddress,
                status,
                domainOrderItems,
                Money.of(totalAmount),
                orderedAt,
                updatedAt
        );
    }
}
