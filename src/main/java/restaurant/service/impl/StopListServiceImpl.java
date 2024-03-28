package restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.response.SimpleResponse;
import restaurant.entities.MenuItem;
import restaurant.entities.StopList;
import restaurant.exceptions.NotFoundException;
import restaurant.repository.StopListRepository;
import restaurant.repository.UserRepository;
import restaurant.service.StopListService;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SimpleResponse save(Principal principal, Long menuId, String reason) {
        List<MenuItem> list = userRepository.getByEmail(principal.getName()).getRestaurant().getMenuItems();
        for (MenuItem item : list) {
            if(item.getId().equals(menuId)){
                if(item.getStopList() == null){
                    StopList stopList = new StopList();
                    stopList.setReason(reason);
                    stopList.setMenuItem(item);
                    item.setStopList(stopList);
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.OK)
                            .message("Successfully saved menu to stop List")
                            .build();
                }
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("This menu is already in the stop list")
                        .build();

            }
        }
        throw new NotFoundException("This menu is not exists! ");
    }
}
