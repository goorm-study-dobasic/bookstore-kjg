package goorm.bookstore.deliveryaddress.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DeliveryAddressInfoDto {


    @NotBlank(message = "배송지 이름을 입력해주세요.")
    private String addressName;

    @NotBlank(message = "우편번호를 입력해주세요.")
    private String zipcode;

    @NotBlank(message = "도로명 주소를 입력해주세요.")
    private String streetAddr;

    @NotBlank(message = "상세주소를 입력해주세요.")
    private String detailAddr;

    private String etc;


    @Builder
    public DeliveryAddressInfoDto(String addressName, String zipcode, String streetAddr, String detailAddr, String etc) {
        this.addressName = addressName;
        this.zipcode = zipcode;
        this.streetAddr = streetAddr;
        this.detailAddr = detailAddr;
        this.etc = etc;
    }
}
