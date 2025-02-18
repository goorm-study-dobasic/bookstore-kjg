package goorm.bookstore.user.dto;

import goorm.bookstore.user.domain.User;
import goorm.bookstore.user.domain.UserRole;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JoinUserDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이여야 합니다.")
    @Pattern(regexp = ".*[a-zA-Z].*", message = "비밀번호는 최소 하나의 영문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "휴대폰 번호를 입력해야 합니다.")
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = "휴대폰 번호는 010-0000-0000 형식이어야 합니다.")
    private String phone;


    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "우편번호를 입력해주세요.")
    private String zipcode;

    @NotBlank(message = "도로명 주소를 입력해주세요.")
    private String streetAddr;

    @NotBlank(message = "상세주소를 입력해주세요.")
    private String detailAddr;

    private String etc;

    public static User toUser(JoinUserDto joinUserDto) {
        User user = new User();

        user.setEmail(joinUserDto.getEmail());
        user.setPassword(joinUserDto.getPassword());
        user.setPhone(joinUserDto.getPhone());
        user.setNickname(joinUserDto.getNickname());
        user.setRole(UserRole.ROLE_USER);
        user.setGrade("BRONZE");
        user.setMileage(0);
        user.setUseYn('Y');
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }
}
