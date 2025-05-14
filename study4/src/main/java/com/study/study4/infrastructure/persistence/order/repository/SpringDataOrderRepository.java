package com.study.study4.infrastructure.persistence.order.repository;

import com.study.study4.domain.order.OrderStatus;
import com.study.study4.infrastructure.persistence.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus status);

    List<OrderEntity> findByCustomerEmail(String email);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderedAt BETWEEN :startDate AND :endDate")
    List<OrderEntity> findByOrderDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * 고객별 주문 수량 조회
     * List<Object[]>
     *     customerEmail, count(*)
     */
    @Query("SELECT o.customerEmail, COUNT(o) FROM OrderEntity o GROUP BY o.customerEmail")
    List<Object[]> countOrdersByCustomer();

    /**
     * 상태별 주문 수량 조회
     * List<Object[]>
     *     status, count(*)
     */
    @Query("SELECT o.status, COUNT(o) FROM OrderEntity o GROUP BY o.stats")
    List<Object[]> countOrdersByStatus();

    /**
     * 최근 주문 조회
     * query method
     */
    List<OrderEntity> findTop10ByOrderByOrderedAtDesc();
}
