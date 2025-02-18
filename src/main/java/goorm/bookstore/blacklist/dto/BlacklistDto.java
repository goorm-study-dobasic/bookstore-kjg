package goorm.bookstore.blacklist.dto;

import goorm.bookstore.blacklist.domain.Blacklist;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlacklistDto {

    @NotBlank(message = "대상 유저 이메일은 필수로 입력해주세요.")
    private String email;

    @NotBlank(message = "사유는 필수로 입력해주세요.")
    private String reason;

    private LocalDateTime blacklistedAt;
    private LocalDateTime unleashedAt;

    @NotBlank(message = "관리자명을 입력해주세요.")
    private String blacklistedBy;
    private String unleashedBy;

    @Builder
    public BlacklistDto(String email, String reason, String blacklistedBy, String unleashedBy, LocalDateTime blacklistedAt, LocalDateTime unleashedAt) {
        this.email = email;
        this.reason = reason;
        this.blacklistedBy = blacklistedBy;
        this.blacklistedAt = blacklistedAt;
        this.unleashedBy = unleashedBy;
        this.unleashedAt = unleashedAt;
    }



}
