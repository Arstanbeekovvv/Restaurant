package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.MenuItemRequest;
import restaurant.dto.response.MenuResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.enums.Sort;
import restaurant.service.MenuItemService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menuItem")
public class MenuItemAPI {
    private final MenuItemService menuItemService;

    // save
    @Secured({"ADMIN", "CHEF"})
    @PostMapping("/save/{restId}")
    public SimpleResponse save(Principal principal,
                               @RequestBody MenuItemRequest menuItemRequest ){
        return menuItemService.save(principal, menuItemRequest);
    }

    @Secured({"ADMIN", "WAITER"})
    @GetMapping("/get-menu")
    public List<MenuResponse> getMenus(Principal principal,
                                       @RequestBody String globalSearch){
        return menuItemService.globalSearch(principal, globalSearch);
    }

    @Secured({"ADMIN", "WAITER"})
    @GetMapping("/get-sort")
    public List<MenuResponse> getSort(Principal principal,
                                       @RequestBody Sort sort){
        return menuItemService.sort(principal, sort);
    }

    @Secured({"ADMIN", "WAITER"})
    @GetMapping("/vegan-sort")
    public List<MenuResponse> veganSort(Principal principal,
                                      @RequestBody boolean sort){
        return menuItemService.sortByVegan(principal, sort);
    }





}
