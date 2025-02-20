package goorm.bookstore.orderItem.domain;

import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.order.domain.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    // 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    // 주문상품과 주문은 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
