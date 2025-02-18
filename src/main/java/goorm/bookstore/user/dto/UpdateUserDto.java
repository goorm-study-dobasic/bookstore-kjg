package goorm.bookstore.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    private String email;
    private String phone;
    private String nickname;
    private String grade;
    private int mileage;
    private char useYn;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
