package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByEmail(String email);
}
