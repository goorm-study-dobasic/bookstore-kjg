package goorm.bookstore.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchBookDto {

    @NotBlank(message = "검색어를 입력해주세요")
    private String query;

    public SearchBookDto(String query) {
        this.query = query;
    }
}
