package restaurant.service;

import restaurant.dto.response.SimpleResponse;

import java.security.Principal;

public interface StopListService {
    SimpleResponse save(Principal principal, Long menuId, String reason);

}
