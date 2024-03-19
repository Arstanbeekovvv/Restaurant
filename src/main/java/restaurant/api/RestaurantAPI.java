package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.service.RestaurantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class RestaurantAPI {
    private final RestaurantService restaurantService;

    
}
