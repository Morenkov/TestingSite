package ru.ibs.testing.site.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibs.testing.site.dto.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
