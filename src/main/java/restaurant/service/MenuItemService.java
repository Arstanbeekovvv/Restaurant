package restaurant.service;

import restaurant.dto.request.MenuItemRequest;
import restaurant.dto.response.MenuResponse;
import restaurant.dto.response.SimpleResponse;

import java.security.Principal;
import java.util.List;

public interface MenuItemService {
    SimpleResponse save(Principal principal, MenuItemRequest menuItemRequest);

    List<MenuResponse> globalSearch(Principal principal, String globalSearch);

}
