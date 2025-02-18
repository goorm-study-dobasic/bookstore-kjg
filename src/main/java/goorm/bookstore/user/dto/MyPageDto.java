package goorm.bookstore.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyPageDto {

    private String email;
    private String phone;

    @NotBlank(message = "닉네임은 공란미면 안돼요")
    private String nickname;

    private String grade;
    private int mileage;

    @Builder
    public MyPageDto(String email, String phone, String nickname, String grade, int mileage) {
        this.email = email;
        this.phone = phone;
        this.nickname = nickname;
        this.grade = grade;
        this.mileage = mileage;
    }
}

