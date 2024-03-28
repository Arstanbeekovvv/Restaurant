package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);

    @Query("select count(u) from User u where u.restaurant.id = ?1")
    int getByRestaurantId(Long id);
}
