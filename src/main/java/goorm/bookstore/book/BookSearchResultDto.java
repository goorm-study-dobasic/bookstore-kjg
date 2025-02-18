package goorm.bookstore.book;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookSearchResultDto {

    private String thumbnail;
    private String title;
    private String isbn;


}
