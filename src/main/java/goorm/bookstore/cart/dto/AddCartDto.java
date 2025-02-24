package goorm.bookstore.cart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartDto {

    private String email;

    private Long inventoryId;

    private int quantity;

}
