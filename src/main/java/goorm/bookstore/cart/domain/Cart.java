package goorm.bookstore.cart.domain;

import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class
Cart {

    protected Cart() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    // 다대일 관계.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory; // 책이다 책

    private int quantity;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime addedAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Builder
    public Cart(User user, Inventory inventory, int quantity) {
        this.user = user;
        this.inventory = inventory;
        this.quantity = quantity;
    }

    public void changeQuantity(int quantity) {
        // 장바구니에 담은 개별 상품의 수량 변경
        this.quantity = quantity;
    }
    public void addQuantity(int quantity) {
        // 장바구니에 담은 개별 상품의 수량 변경
        this.quantity += quantity;
    }

}
