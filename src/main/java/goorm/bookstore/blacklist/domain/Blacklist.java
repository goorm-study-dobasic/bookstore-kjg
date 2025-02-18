package goorm.bookstore.blacklist.domain;

import goorm.bookstore.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blacklistSeq;

    @OneToOne
    @JoinColumn(name = "user_seq")
    private User user;

    private String reason;
    private LocalDateTime blacklistedAt;
    private LocalDateTime unleashedAt;
    private String blacklistedBy;
    private String unleashedBy;

    @Builder
    public Blacklist(User user,
                     String reason,
                     String blacklistedBy) {
        this.user = user;
        this.reason = reason;
        this.blacklistedAt = LocalDateTime.now();
        this.blacklistedBy = blacklistedBy;
    }
}
