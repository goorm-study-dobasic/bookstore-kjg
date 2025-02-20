package goorm.bookstore.deliveryaddress.domain;

import goorm.bookstore.user.domain.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(
        name = "DeliveryAddressInfo",
        uniqueConstraints = @UniqueConstraint(name = "user_seq to addressName", columnNames = {"user_id", "addressName"})
)
@NoArgsConstructor
public class DeliveryAddressInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_info_id")
    private Long id;

    // 유저가 들어있어서 유저도 조회해야함. 유저 조회가 필요할 때 조인 쿼리가 나가야 함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String addressName;

    private String zipcode;
    private String streetAddr;
    private String detailAddr;


    private String etc;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder
    public DeliveryAddressInfo(User user, String addressName, String zipcode, String streetAddr, String detailAddr, String etc) {
        this.user = user;
        this.addressName = addressName;
        this.zipcode = zipcode;
        this.streetAddr = streetAddr;
        this.detailAddr = detailAddr;
        this.etc = etc;
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }
}
