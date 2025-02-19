package goorm.bookstore.inventory.dto;

import goorm.bookstore.inventory.domain.InventoryStatus;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UpdateInventoryDto {

    private Long inventoryId;

    @Min(value = 0, message = "음수는 입력할 수 없어요.")
    private int quantity;
    private InventoryStatus status;
}
