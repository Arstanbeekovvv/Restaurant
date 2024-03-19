package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAPI {
    private final UserService userService;



}
