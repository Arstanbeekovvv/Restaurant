package restaurant.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restaurant.config.jwt.JwtService;
import restaurant.dto.request.SignInRequest;
import restaurant.dto.request.SignUpRequest;
import restaurant.dto.response.SignResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.entities.Restaurant;
import restaurant.entities.User;
import restaurant.enums.Role;
import restaurant.exceptions.NotFoundException;
import restaurant.repository.JobAppRepository;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.UserRepository;
import restaurant.service.UserService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RestaurantRepository restaurantRepository;
    private final JobAppRepository jobAppRepository;

    @Override
    public SignResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getByEmail(signInRequest.email());

        boolean matches = passwordEncoder.matches(signInRequest.password(), user.getPassword());
        if (!matches) throw new NotFoundException("Invalid password");

        return SignResponse.builder()
                .token(jwtService.createToken(user))
                .id(user.getId())
                .role(user.getRole().name())
                .httpStatus(HttpStatus.OK)
                .message("Successful login")
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse signUp(Long resId, SignUpRequest signUpRequest) {
        User saveApps = userRepository.save(User.builder()
                        .firstName(signUpRequest.firstName())
                        .lastName(signUpRequest.lastName())
                        .email(signUpRequest.email())
                        .password(passwordEncoder.encode(signUpRequest.password()))
                        .dateOfBirth(signUpRequest.dateOfBirth())
                        .phoneNumber(signUpRequest.phoneNumber())
                        .experience(signUpRequest.experience())
                        .role(signUpRequest.role())
                        .build()
        );
        Restaurant restaurant = restaurantRepository.getRestaurantById(resId);
        restaurant.addJobApp(saveApps);
        saveApps.setRestaurant(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("thank you for contacting us," +
                        " we will definitely consider your application")
                .build();
    }


    @PostConstruct
    private void initMethode() {
        userRepository.save(User.builder()
                        .firstName("Mirlan")
                        .lastName("Arstanbekov")
                        .email("arstanbeekovvv@gmail.com")
                        .password(passwordEncoder.encode("arstanbeekovvv"))
                        .dateOfBirth(LocalDate.of(2002,4,7))
                        .phoneNumber("+996771900091")
                        .experience(99)
                        .role(Role.ADMIN)
                .build());
    }
}
