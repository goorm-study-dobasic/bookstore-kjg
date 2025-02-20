package goorm.bookstore.cart.dto;

import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.user.domain.User;

public class AddCartDto {

    private User user;
    private Inventory inventory;
    private int quantity;
}
