package goorm.bookstore.deliveryaddress.repository;

import goorm.bookstore.deliveryaddress.domain.DeliveryAddressInfo;
import goorm.bookstore.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryServiceInfoRepository extends JpaRepository<DeliveryAddressInfo, Long> {
    List<DeliveryAddressInfo> findByUser(User user);

    Optional<DeliveryAddressInfo> findByUserAndAddressName(User user, String addressName);

    void deleteByUserAndAddressName(User user, String addressName);
}
