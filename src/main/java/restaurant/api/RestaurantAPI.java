package restaurant.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.RestaurantRequest;
import restaurant.dto.request.RestaurantRequestUpdate;
import restaurant.dto.response.RestaurantResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.dto.response.UserResponse;
import restaurant.service.RestaurantService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class RestaurantAPI {
    private final RestaurantService restaurantService;

    @Secured({"ADMIN"})
    @GetMapping("/job-app")
    public List<UserResponse> getJobApps(Principal principal) {
        return restaurantService.getJobApps(principal);
    }

    @Secured({"ADMIN"})
    @PutMapping("/job-app/{userId}")
    public SimpleResponse assignEmployees(Principal principal,
                                          @PathVariable Long userId) {
        return restaurantService.assignEmployToRest(principal, userId);
    }


    @Secured({"ADMIN"})
    @GetMapping("/count-users")
    public Integer getCountUsersRest(Principal principal) {
        return restaurantService.countUsersRest(principal);
    }

    // save
    @Secured("DEVELOPER")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        return restaurantService.save(restaurantRequest);
    }

    // find by id
    @Secured({"DEVELOPER", "ADMIN"})
    @GetMapping("/get/{restId}")
    public RestaurantResponse findById(Principal principal,
                                       @PathVariable Long restId) {
        return restaurantService.findById(principal, restId);
    }

    // find all
    @Secured("DEVELOPER")
    @GetMapping("/get-all")
    public List<RestaurantResponse> findAll() {
        return restaurantService.findAll();
    }

    // update
    @Secured("ADMIN")
    @PutMapping("/update/{restId}")
    public SimpleResponse update(Principal principal,
                                 @PathVariable Long restId,
                                 @RequestBody RestaurantRequestUpdate restaurantRequest) {
        return restaurantService.update(principal, restId, restaurantRequest);
    }

    // delete
    @Secured(("DEVELOPER"))
    @DeleteMapping("/delete/{restId}")
    public SimpleResponse delete(@PathVariable Long restId) {
        return restaurantService.delete(restId);
    }
}
