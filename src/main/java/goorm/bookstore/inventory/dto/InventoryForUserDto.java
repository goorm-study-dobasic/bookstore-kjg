package goorm.bookstore.inventory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryForUserDto {

    private Long inventoryId;
    private String title;
    private String isbn;
    private String authors;
    private String publisher;
    private int salePrice;
    private String thumbnail;
}
