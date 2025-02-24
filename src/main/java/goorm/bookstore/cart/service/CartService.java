package goorm.bookstore.cart.service;

import goorm.bookstore.cart.domain.Cart;
import goorm.bookstore.cart.dto.AddCartDto;
import goorm.bookstore.cart.dto.CartDto;
import goorm.bookstore.cart.dto.UpdateCartDto;
import goorm.bookstore.cart.repository.CartRepository;
import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.inventory.repository.InventoryRepository;
import goorm.bookstore.user.domain.User;
import goorm.bookstore.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;


    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, InventoryRepository inventoryRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
    }

    // 추가
    @Transactional
    public void save(AddCartDto addCartDto) {

        User user = userRepository.findByEmail(addCartDto.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("해당되는 이메일을 가진 유저가 존재하지 않습니다."));

        Inventory inventory = inventoryRepository.findById(addCartDto.getInventoryId()).orElseThrow(
                () -> new EntityNotFoundException("해당되는 책이 존재하지 않습니다."));

        // 이미 장바구니에 있는 경우
        Optional<Cart> findCart = cartRepository.findCartByUserAndInventory(user, inventory);
        if (findCart.isPresent()) {
            findCart.get().addQuantity(addCartDto.getQuantity());
            return;
        }

        Cart cart = Cart.builder()
                    .user(user)
                    .inventory(inventory)
                    .quantity(addCartDto.getQuantity()).build();
        cartRepository.save(cart);
    }

    @Transactional
    // 수정 : 장바구니에 담긴 책 수량을 조정한다.
    public void updateById(UpdateCartDto updateCartDto) {
        Cart cart = cartRepository.findById(updateCartDto.getCartId()).orElseThrow(
                () -> new EntityNotFoundException("장바구니에 해당되는 책이 존재하지 않습니다."));

        cart.changeQuantity(updateCartDto.getQuantity());
    }


    @Transactional
    // 삭제 : 장바구니에 담은 책을 삭제한다.
    public void deleteById(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    // 장바구니에 있는 상품 하나를 조회한다. 아이디로 조회하면 되겠지?
    public CartDto findById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new EntityNotFoundException("장바구니에 해당되는 상품이 없습니다.")
        );
        return CartDto.createCartDtoFromCart(cart);
    }

    // 전체조회
    public List<CartDto> findAll(String username) {
        // Cart -> CartDto 로 변경해야 함.

        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("해당되는 이메일을 가진 유저가 존재하지 않습니다."));

        List<Cart> cartByUser = cartRepository.findCartByUser(user);
        return cartByUser.stream().map(CartDto::createCartDtoFromCart).toList();
    }
}
