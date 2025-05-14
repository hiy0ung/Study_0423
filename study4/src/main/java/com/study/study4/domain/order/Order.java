package com.study.study4.domain.order;

import com.study.study4.domain.shared.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    private OrderId id;
    private String customerName;
    private String customerEmail;
    private String shippingAddress;
    private OrderStatus status;
    private List<OrderItem> orderItems = new ArrayList<>();
    private Money totalAmount;
    private LocalDateTime orderedAt;
    private LocalDateTime updatedAt;

    @Builder
    public Order(String customerName, String customerEmail,
                 String shippingAddress, List<OrderItem> orderItems) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.CREATED;
        this.orderItems = orderItems;
        this.totalAmount = calculateTotalAmount();
        this.orderedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        validate();
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED || status == OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("배송 중이거나 배송 완료된 주문은 취소할 수 없습니다.");
        }

        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public static Order create(String customerName, String customerEmail,
                               String shippingAddress, List<OrderItem> orderItems) {
        return Order.builder()
                .customerEmail(customerEmail)
                .customerEmail(customerEmail)
                .shippingAddress(shippingAddress)
                .orderItems(orderItems)
                .build();
    }

    private void validate() {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("고객 이름은 필수입니다.");
        }

        // customer email
        // shipping address
        // orderItems
    }

    private Money calculateTotalAmount() {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(Money.ZERO, Money::add);
    }

    public static Order reconstitute(OrderId id, String customerName, String customerEmail,
                                    String shippingAddress, OrderStatus status,
                                    List<OrderItem> orderItems, Money totalAmount,
                                    LocalDateTime orderedAt, LocalDateTime updatedAt) {
        Order order = new Order();
        order.id = id;
        order.customerName = customerName;
        order.shippingAddress = shippingAddress;
        order.status = status;
        order.orderItems = orderItems;
        order.totalAmount = totalAmount;
        order.orderedAt = orderedAt;
        order.updatedAt = updatedAt;
        return order;
    }
}
