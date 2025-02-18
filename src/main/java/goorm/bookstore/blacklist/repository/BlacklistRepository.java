package goorm.bookstore.blacklist.repository;

import goorm.bookstore.blacklist.domain.Blacklist;
import goorm.bookstore.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    Optional<Blacklist> findByUser(User user);

    void deleteByUser(User user);

}
