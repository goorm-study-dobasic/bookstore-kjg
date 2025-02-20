package goorm.bookstore.user.domain;

import goorm.bookstore.cart.domain.Cart;
import goorm.bookstore.order.domain.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /*   // 필요하다면 장바구니 목록 양방향 참조
    @OneToMany(mappedBy = "user")
    private List<Cart> cart = new ArrayList<>();
    */

    /*  // 필요하다면 주문 양방향 참조
    @OneToMany(mappedBy = "user")
    private List<Order> orderList = new ArrayList<>();
    */


    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    private String nickname;

    private String grade;

    private int mileage;

    private char useYn;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;
}
