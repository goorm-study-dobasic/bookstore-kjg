package goorm.bookstore;

import goorm.bookstore.user.domain.User;
import goorm.bookstore.user.domain.UserRole;
import goorm.bookstore.user.dto.JoinUserDto;
import goorm.bookstore.user.repository.UserRepository;
import goorm.bookstore.user.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


// @Component
public class InitData {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitData(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //@PostConstruct
    public void initData() {


        User user = new User();
        user.setEmail("test@naver.com");
        user.setPhone("010-3395-6517");
        user.setNickname("zz");
        String password = bCryptPasswordEncoder.encode("1234");
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setGrade("BRONZE");
        user.setMileage(0);
        user.setUseYn('Y');
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(UserRole.ROLE_ADMIN);

        userRepository.save(user);

    }
}
