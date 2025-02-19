package goorm.bookstore.inventory.dto;

import goorm.bookstore.inventory.domain.InventoryStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InventoryForAdminDto {

    private Long inventoryId;
    private String title;
    private String isbn;
    private String authors;
    private String publisher;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;
    private int price;
    private int salePrice;
    private String thumbnail;
    private int quantity;
    private InventoryStatus status;
    private String translators;

    @Builder
    public InventoryForAdminDto(Long inventoryId, String title, String isbn, String authors, String publisher, LocalDateTime createdAt,
                                LocalDateTime lastModifiedAt, String createdBy, String lastModifiedBy, int price, int salePrice,
                                String thumbnail, int quantity, InventoryStatus status, String translators) {
        this.inventoryId = inventoryId;
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.price = price;
        this.salePrice = salePrice;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
        this.status = status;
        this.translators = translators;
    }
}
