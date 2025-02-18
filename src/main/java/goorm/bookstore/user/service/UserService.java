package goorm.bookstore.user.service;

import goorm.bookstore.deliveryaddress.domain.DeliveryAddressInfo;
import goorm.bookstore.deliveryaddress.dto.DeliveryAddressInfoDto;
import goorm.bookstore.deliveryaddress.repository.DeliveryServiceInfoRepository;
import goorm.bookstore.user.domain.User;
import goorm.bookstore.user.dto.JoinUserDto;
import goorm.bookstore.user.dto.MyPageDto;
import goorm.bookstore.user.dto.UpdateUserDto;
import goorm.bookstore.user.dto.UserDto;
import goorm.bookstore.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final DeliveryServiceInfoRepository deliveryServiceInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       DeliveryServiceInfoRepository deliveryServiceInfoRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.deliveryServiceInfoRepository = deliveryServiceInfoRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(JoinUserDto joinUserDto) {
        // 신규 유저 저장 JoinUserDto
        // userDto -> user -> repository.save(user)

        // JoinUserDto -> User
        joinUserDto.setPassword(bCryptPasswordEncoder.encode(joinUserDto.getPassword()));
        User user = JoinUserDto.toUser(joinUserDto);

        DeliveryAddressInfo deliveryAddressInfo = DeliveryAddressInfo.builder()
                .user(user)
                .addressName("기본 배송지")
                .zipcode(joinUserDto.getZipcode())
                .streetAddr(joinUserDto.getStreetAddr())
                .detailAddr(joinUserDto.getDetailAddr())
                .etc(joinUserDto.getEtc())
                .build();

        userRepository.save(user);
        deliveryServiceInfoRepository.save(deliveryAddressInfo);
    }

    public void save(MyPageDto myPageDto) {
        // 마이페이지에서 닉네임 수정
        User user = userRepository.findByEmail(myPageDto.getEmail())
                .orElseThrow( () -> new EntityNotFoundException("존재하지 않는 이메일"));

        user.setLastModifiedAt(LocalDateTime.now());
        user.setNickname(myPageDto.getNickname());

        // Authentication
        CustomUserDetails updatedUserDetails = new CustomUserDetails(user);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(updatedUserDetails, null, updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public UserDto findByEmail(String email) {
        // 이메일로 유저 찾아서 Dto 객체로 반환
        // 예외처리 필요.
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException("User not found with email : " + email));
        return UserDto.toDto(user);
    }

    public List<UserDto> findAll() {
        // 전체 유저 리스트를 받아서 UserDto 로 변환해서 반환.
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::toDto).toList();
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID : " + id));
        return UserDto.toDto(user);
    }

    public void update(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id : " + id));

        // 닉네임만 변경 가능함.
        user.setNickname(updateUserDto.getNickname());
        user.setLastModifiedAt(LocalDateTime.now());
    }

    public void deactivate(String email) {
        // 삭제
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email : " + email));

        // 비활성화
        user.setUseYn('N');
        user.setLastModifiedAt(LocalDateTime.now());
    }

    public boolean checkDuplicateEmail(String email) {
        // 이메일 중복 체크. true 면 이미 존재하는 이메일
        return userRepository.existsByEmail(email);
    }

    public List<UserDto> findByUseYn() {
        return userRepository.findByUseYn('Y').stream().map(UserDto::toDto).toList();
    }
}
