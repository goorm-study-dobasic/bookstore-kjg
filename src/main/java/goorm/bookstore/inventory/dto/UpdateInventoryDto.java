package goorm.bookstore.inventory.dto;

import goorm.bookstore.inventory.domain.InventoryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateInventoryDto {

    private Long inventoryId;
    private int quantity;
    private InventoryStatus status;
}
