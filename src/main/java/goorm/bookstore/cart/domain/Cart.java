package goorm.bookstore.cart.domain;

import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "inventory_id"))
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    // 다대일 관계.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 일대일 관계
    // 일대일 관계는 그 반대도 일대일
    // 외래키에 데이터베이스 유니크 제약조건 추가.
    @OneToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory; // 책이다 책

    private int quantity;

    private LocalDateTime addedAt;

    private LocalDateTime lastModifiedAt;
}
