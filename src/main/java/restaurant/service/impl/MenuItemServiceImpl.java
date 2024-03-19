package restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.MenuItemRequest;
import restaurant.dto.response.MenuResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.entities.MenuItem;
import restaurant.entities.Restaurant;
import restaurant.repository.MenuItemRepository;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.UserRepository;
import restaurant.service.MenuItemService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SimpleResponse save(Principal principal, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());

        Restaurant restaurant = restaurantRepository.getRestaurantById(userRepository.getByEmail(principal.getName()).getRestaurant().getId());
        menuItem.setRestaurant(restaurant);

        menuItemRepository.save(menuItem);

        restaurant.getMenuItems().add(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved menu item!")
                .build();

    }

    @Override
    public List<MenuResponse> globalSearch(Principal principal, String globalSearch) {
        List<MenuResponse> menuResponses = new ArrayList<>();
        for (MenuItem menuItem : userRepository.getByEmail(principal.getName()).getRestaurant().getMenuItems()) {
            if(menuItem.getName().contains(globalSearch)){
                MenuResponse.builder()
                        .name(menuItem.getName())
                        .image(menuItem.getImage())
                        .price(menuItem.getPrice())
                        .description(menuItem.getDescription())
                        .isVegetarian(menuItem.isVegetarian())
                        .build();

            }
        }
        return menuResponses;
    }
}
