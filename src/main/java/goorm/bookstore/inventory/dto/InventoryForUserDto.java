package goorm.bookstore.inventory.dto;

import goorm.bookstore.inventory.domain.InventoryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InventoryForUserDto {

    private Long inventoryId;
    private String title;
    private String isbn;
    private String authors;
    private String publisher;
    private int salePrice;
    private String thumbnail;
    private InventoryStatus status;
}
