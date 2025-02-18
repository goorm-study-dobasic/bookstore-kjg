package goorm.bookstore.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    private String nickname;

    private String grade;

    private int mileage;

    private char useYn;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;
}
