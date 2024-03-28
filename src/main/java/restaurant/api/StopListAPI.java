package restaurant.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.StopListRequest;
import restaurant.dto.response.SimpleResponse;
import restaurant.service.StopListService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stop-list")
public class StopListAPI {
    private final StopListService stopListService;

    @Secured({"ADMIN", "CHEF"})
    @PostMapping("/save/{menuId}")
    public SimpleResponse save(Principal principal,
                               @RequestBody String reason,
                               @PathVariable Long menuId){
        return stopListService.save(principal, menuId, reason);
    }
}
