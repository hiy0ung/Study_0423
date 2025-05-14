package com.study.study4.domain.order;

public enum OrderStatus {
    CREATED, // 주문 생성
    PENDING, // 결제 대기중
    PAID, // 결제 완료
    PREPARING, // 상품 준비중
    SHIPPED, // 배송중
    DELIVERED, // 베송 완료
    CANCELLED, // 주문 취소 됨
    REFUNDED // 환불 처리 됨
}
