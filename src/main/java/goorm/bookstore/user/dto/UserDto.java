package goorm.bookstore.user.dto;

import goorm.bookstore.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String email;
    private String phone;
    private String nickname;
    private String grade;
    private int mileage;
    private char useYn;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setNickname(user.getNickname());
        userDto.setGrade(user.getGrade());
        userDto.setMileage(user.getMileage());
        userDto.setUseYn(user.getUseYn());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setLastModifiedAt(user.getLastModifiedAt());

        return userDto;
    }
}
