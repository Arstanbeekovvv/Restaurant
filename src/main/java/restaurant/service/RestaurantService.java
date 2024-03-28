package restaurant.service;

import restaurant.dto.request.RestaurantRequestUpdate;
import restaurant.dto.request.RestaurantRequest;
import restaurant.dto.response.RestaurantResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.dto.response.UserResponse;

import java.security.Principal;
import java.util.List;

public interface RestaurantService {
    Integer countUsersRest(Principal principal);

    SimpleResponse save(RestaurantRequest restaurantRequest);

    RestaurantResponse findById(Principal principal, Long restId);

    List<RestaurantResponse> findAll();

    SimpleResponse update(Principal principal, Long restId, RestaurantRequestUpdate restaurantRequest);

    SimpleResponse delete(Long restId);

    List<UserResponse> getJobApps(Principal principal);

    SimpleResponse assignEmployToRest(Principal principal, Long userId);

}
