package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.entities.Restaurant;
import restaurant.exceptions.NotFoundException;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    default Restaurant getRestaurantById(Long resId){
        return findById(resId).orElseThrow(() ->
                new NotFoundException("Restaurant with id: " +resId+ "not found"));
    };
}
