package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.entities.Restaurant;
import restaurant.exceptions.NotFoundException;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    default Restaurant getRestaurantById(Long resId){
        return findById(resId).orElseThrow(() ->
                new NotFoundException("Restaurant with id: " +resId+ "not found"));
    };

    @Query("select r from Restaurant r join r.menuItems mi join mi.subcategory.category c where c.id= :catId")
    Restaurant getRestaurantByCatId(Long catId);

    @Query("select r from Restaurant r join r.menuItems mi on mi.subcategory.id =:subId")
    Restaurant getRestaurantBySubId(Long subId);
}
