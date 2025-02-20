package goorm.bookstore.orderItem.repository;

import goorm.bookstore.order.domain.Order;
import goorm.bookstore.orderItem.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findOrderItemByOrder(Order order);
}

