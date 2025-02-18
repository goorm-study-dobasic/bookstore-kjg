package goorm.bookstore.inventory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    public Inventory() {
        status = InventoryStatus.ON_SALES;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    private String createdBy;
    private String lastModifiedBy;
    private String title;
    private String contents;
    private String url;
    private String isbn;

    private LocalDateTime datetime; // 출판일

    private String authors;
    private String publisher;
    private String translators;

    private int price;
    private int salePrice;
    private String thumbnail;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private InventoryStatus status;

    @Builder
    public Inventory(String createdBy, String lastModifiedBy, String title, String contents, String url, String isbn, LocalDateTime datetime,String authors, String publisher, String translators, int price, int salePrice, String thumbnail, int quantity){
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.isbn = isbn;
        this.datetime = datetime;
        this.authors = authors;
        this.publisher = publisher;
        this.translators = translators;
        this.price = price;
        this.salePrice = salePrice;
        this.thumbnail = thumbnail;
        this.quantity = Math.max(quantity, 1); // 수량이 1 미만일 경우 1로 설정
    }
}
