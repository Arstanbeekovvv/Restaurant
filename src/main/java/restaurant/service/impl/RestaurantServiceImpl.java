package restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restaurant.dto.request.RestaurantRequestUpdate;
import restaurant.config.jwt.JwtService;
import restaurant.dto.request.RestaurantRequest;
import restaurant.dto.response.RestaurantResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.dto.response.UserResponse;
import restaurant.entities.Restaurant;
import restaurant.entities.User;
import restaurant.enums.Role;
import restaurant.exceptions.ForbiddenException;
import restaurant.exceptions.NotFoundException;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.UserRepository;
import restaurant.service.RestaurantService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Integer countUsersRest(Principal principal) {
        return userRepository.getByRestaurantId(userRepository.getByEmail(principal.getName()).getRestaurant().getId());
    }

    @Override
    @Transactional
    public SimpleResponse save(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());

        restaurantRepository.save(restaurant);

        User user = new User();
        user.setFirstName(restaurantRequest.firstName());
        user.setLastName(restaurantRequest.lastName());
        user.setDateOfBirth(restaurantRequest.dateOfBirth());
        user.setEmail(restaurantRequest.email());
        user.setPassword(passwordEncoder.encode(restaurantRequest.password()));
        user.setPhoneNumber(restaurantRequest.phoneNumber());
        user.setExperience(restaurantRequest.experience());
        user.setRole(Role.ADMIN);
        user.setRestaurant(restaurant);

        userRepository.save(user);
        restaurant.addUser(user);

        restaurant.setNumberOfEmployees(restaurant.getUsers().size());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved!")
                .build();
    }

    @Override
    public RestaurantResponse findById(Principal principal, Long restId) {
        if (userRepository.getByEmail(principal.getName()).getRole().equals(Role.ADMIN) && !userRepository.getByEmail(principal.getName()).getRestaurant().getId().equals(restId)) {
            throw new ForbiddenException("This page is forbidden for you!");
        }
        Restaurant restaurant = restaurantRepository.getRestaurantById(restId);
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .restType(restaurant.getRestType())
                .numberOfEmployees(restaurant.getNumberOfEmployees())
                .service(restaurant.getService())
                .build();
    }

    @Override
    public List<RestaurantResponse> findAll() {
        List<Restaurant> all = restaurantRepository.findAll();
        List<RestaurantResponse> allResponse = new ArrayList<>();
        for (Restaurant restaurant : all) {
            allResponse.add(RestaurantResponse.builder()
                    .id(restaurant.getId())
                    .name(restaurant.getName())
                    .location(restaurant.getLocation())
                    .restType(restaurant.getRestType())
                    .numberOfEmployees(restaurant.getNumberOfEmployees())
                    .service(restaurant.getService())
                    .build());
        }
        return allResponse;
    }

    @Override
    @Transactional
    public SimpleResponse update(Principal principal, Long restId, RestaurantRequestUpdate restaurantRequest) {
        if (userRepository.getByEmail(principal.getName()).getRole().equals(Role.ADMIN) && !userRepository.getByEmail(principal.getName()).getRestaurant().getId().equals(restId)) {
            throw new ForbiddenException("This page is forbidden for you!");
        }
        Restaurant restaurant = restaurantRepository.getRestaurantById(restId);
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setNumberOfEmployees(restaurant.getUsers().size());
        restaurant.setService(restaurantRequest.service());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long restId) {
        restaurantRepository.delete(restaurantRepository.getRestaurantById(restId));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted!")
                .build();
    }

    @Override
    public List<UserResponse> getJobApps(Principal principal) {
        List<User> list = userRepository.getByEmail(principal.getName()).getRestaurant().getJobApps();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : list) {
            userResponses.add(UserResponse.builder()
                    .id(user.getId())
                    .fullName(user.getFirstName() + " " + user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole())
                    .experience(user.getExperience())
                    .build());
        }
        return userResponses;
    }

    @Override
    @Transactional
    public SimpleResponse assignEmployToRest(Principal principal, Long userId) {
        Restaurant restaurant = userRepository.getByEmail(principal.getName()).getRestaurant();
        if(!restaurant.getJobApps().contains(userRepository.getReferenceById(userId))){
            throw new NotFoundException("There is no such user!");
        }
        User user1 = restaurant.getJobApps().stream().filter(user -> user.getId().equals(userId)).findFirst().orElseThrow();
        restaurant.addUser(user1);
        restaurant.getJobApps().remove(user1);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully assign employ to restaurant!")
                .build();
    }
}
