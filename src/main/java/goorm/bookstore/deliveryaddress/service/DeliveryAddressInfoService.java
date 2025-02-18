package goorm.bookstore.deliveryaddress.service;

import goorm.bookstore.deliveryaddress.domain.DeliveryAddressInfo;
import goorm.bookstore.deliveryaddress.dto.DeliveryAddressInfoDto;
import goorm.bookstore.deliveryaddress.repository.DeliveryServiceInfoRepository;
import goorm.bookstore.user.domain.User;
import goorm.bookstore.user.repository.UserRepository;
import goorm.bookstore.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DeliveryAddressInfoService {

    private final DeliveryServiceInfoRepository deliveryServiceInfoRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeliveryAddressInfoService(DeliveryServiceInfoRepository deliveryServiceInfoRepository, UserRepository userRepository) {
        this.deliveryServiceInfoRepository = deliveryServiceInfoRepository;
        this.userRepository = userRepository;
    }

    public void save(String email, DeliveryAddressInfoDto deliveryAddressInfoDto) {
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new EntityNotFoundException("해당되는 이메일을 가진 유저가 없습니다."));

        DeliveryAddressInfo deliveryAddressInfo = DeliveryAddressInfo.builder()
                .user(user)
                .addressName(deliveryAddressInfoDto.getAddressName())
                .zipcode(deliveryAddressInfoDto.getZipcode())
                .streetAddr(deliveryAddressInfoDto.getStreetAddr())
                .detailAddr(deliveryAddressInfoDto.getDetailAddr())
                .etc(deliveryAddressInfoDto.getEtc())
                .build();

        deliveryAddressInfo.setCreatedAt(LocalDateTime.now());
        deliveryServiceInfoRepository.save(deliveryAddressInfo);
    }

    public void update(String email, DeliveryAddressInfoDto deliveryAddressInfoDto, String previousAddressName) {
        // 조회 후 set 하면 끝
        // 조회에 필요한 데이터..
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("이메일에 해당하는 유저가 없습니다."));

        DeliveryAddressInfo deliveryAddressInfo = deliveryServiceInfoRepository.findByUserAndAddressName(user, previousAddressName).orElseThrow(() -> new EntityNotFoundException("해당되는 배송지가 없습니다."));

        deliveryAddressInfo.setAddressName(deliveryAddressInfoDto.getAddressName());
        deliveryAddressInfo.setZipcode(deliveryAddressInfoDto.getZipcode());
        deliveryAddressInfo.setStreetAddr(deliveryAddressInfoDto.getStreetAddr());
        deliveryAddressInfo.setDetailAddr(deliveryAddressInfoDto.getDetailAddr());
        deliveryAddressInfo.setEtc(deliveryAddressInfoDto.getEtc());
    }

    public List<DeliveryAddressInfoDto> findByUser(String email) {
        // 유저 시퀀스임
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("이메일에 해당하는 유저가 없습니다."));

        List<DeliveryAddressInfo> byUser = deliveryServiceInfoRepository.findByUser(user);

        return byUser.stream().map(addressInfo ->
                DeliveryAddressInfoDto.builder()
                        .addressName(addressInfo.getAddressName())
                        .zipcode(addressInfo.getZipcode())
                        .streetAddr(addressInfo.getStreetAddr())
                        .detailAddr(addressInfo.getDetailAddr())
                        .etc(addressInfo.getEtc())
                        .build())
                .toList();
    }

    public boolean checkDuplicateAddressName(String email, String addressName) {
        // 유저 id로 리스트 검색.
        List<DeliveryAddressInfoDto> userAddressList = findByUser(email);

        for (DeliveryAddressInfoDto deliveryAddressInfoDto : userAddressList) {
            if (deliveryAddressInfoDto.getAddressName().equals(addressName)) {
                return true;
            }
        }
        return false;
    }


    public void delete(String email, String addressName) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("이메일에 해당하는 유저가 없습니다."));
        deliveryServiceInfoRepository.deleteByUserAndAddressName(user, addressName);
    }
}
