package goorm.bookstore.blacklist.service;

import goorm.bookstore.blacklist.domain.Blacklist;
import goorm.bookstore.blacklist.dto.BlacklistDto;
import goorm.bookstore.blacklist.repository.BlacklistRepository;
import goorm.bookstore.user.domain.User;
import goorm.bookstore.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BlacklistService {

    private final UserRepository userRepository;
    private final BlacklistRepository blacklistRepository;

    @Autowired
    public BlacklistService(UserRepository userRepository, BlacklistRepository blacklistRepository) {
        this.userRepository = userRepository;
        this.blacklistRepository = blacklistRepository;
    }

    // 블랙리스트 등록 + 유저 상태 Y -> N 변경.
    public void save(BlacklistDto blacklistDto) {

        // 해당되는 유저 찾기
        User user = userRepository.findByEmail(blacklistDto.getEmail()).
                orElseThrow(() -> new EntityNotFoundException("이메일에 해당되는 유저가 없습니다."));

        // Blacklist 엔티티 생성
        Blacklist blacklist = Blacklist.builder()
                .user(user)
                .reason(blacklistDto.getReason())
                .blacklistedBy(blacklistDto.getBlacklistedBy())
                .build();

        // 블랙리스트 저장
        blacklistRepository.save(blacklist);

        // 유저 Y -> N 으로 변경
        user.setUseYn('N');
    }


    public List<BlacklistDto> findAll() {
        return blacklistRepository.findAll().stream()
                .map(blacklist -> BlacklistDto.builder()
                        .email(blacklist.getUser().getEmail())
                        .blacklistedAt(blacklist.getBlacklistedAt())
                        .blacklistedBy(blacklist.getBlacklistedBy())
                        .reason(blacklist.getReason())
                        .unleashedAt(blacklist.getUnleashedAt())
                        .unleashedBy(blacklist.getUnleashedBy())
                        .build())
                .toList();
    }

    public void delete(String email) {
        // 해당되는 유저 찾기
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new EntityNotFoundException("이메일에 해당되는 유저가 없습니다."));

        // 블랙리스트 테이블에서 해당되는 유저 삭제
        blacklistRepository.deleteByUser(user);

        // 유저는 다시 활성화
        user.setUseYn('Y');
    }
}
