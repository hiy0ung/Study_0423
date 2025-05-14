package com.study.study4.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 주문 리포지토리 인터페이스
 * 도메인 계층에서 정의 되어있고, 인프라스트럭처 계층에서 구현
 */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(OrderId id);

    List<Order> findAll();

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerEmail(String email);

    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    void delete(Order order);
}
