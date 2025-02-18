package goorm.bookstore.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import goorm.bookstore.inventory.domain.InventoryStatus;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AddInventoryDto {

    private String title;
    private String contents;
    private String url;
    private String isbn;

    //"2014-11-17T00:00:00.000+09:00"
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime datetime;

    private String[] authors;
    private String publisher;
    private String[] translators;
    private int price;
    private int salePrice;
    private String thumbnail;
    private InventoryStatus status;

    @Min(value = 1, message = "한개 이상의 수량을 입력해주세요.")
    private int quantity;

   }

