package goorm.bookstore.cart.repository;

import goorm.bookstore.cart.domain.Cart;
import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("select c from Cart c join fetch c.inventory join fetch c.user where c.user = :user")
    List<Cart> findCartByUser(@Param("user") User user); // 유저의 장바구니 목록 조회


    void deleteCartsById(Long id); // 장바구니 삭제


    // 이미 유저가 장바구니 담은 상품인지 체크
    Optional<Cart> findCartByUserAndInventory(User user, Inventory inventory);


    List<Cart> findByUserAndInventory(User user, Inventory inventory);
}
