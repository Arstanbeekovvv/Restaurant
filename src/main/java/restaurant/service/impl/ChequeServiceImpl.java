package restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.ChequeRequest;
import restaurant.dto.response.ChequeResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.entities.Cheque;
import restaurant.entities.MenuItem;
import restaurant.entities.Restaurant;
import restaurant.entities.User;
import restaurant.repository.ChequeRepository;
import restaurant.repository.MenuItemRepository;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.UserRepository;
import restaurant.service.ChequeService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public Integer sum(Long waiterId, LocalDate localDate) {
        User user = userRepository.getReferenceById(waiterId);
        int sum = 0;
        for (Cheque cheque : user.getCheques()) {
            if(localDate.equals(cheque.getCreatedAt())) sum = sum + cheque.getPriceAvg().intValue();
        }
        return sum;
    }

    @Override
    @Transactional
    public SimpleResponse update(Long chequeId, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.getChequeById(chequeId);
        cheque.setPriceAvg(chequeRequest.priceAvg());
        cheque.setCreatedAt(chequeRequest.createdAt());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated cheque! ")
                .build();
    }

    @Override
    public SimpleResponse delete(Long chequeId) {
        chequeRepository.delete(chequeRepository.getChequeById(chequeId));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted by this id: "+ chequeId)
                .build();
    }

    @Override
    public Integer avgCheque(Long restId, LocalDate localDate) {
        Restaurant restaurant = restaurantRepository.getRestaurantById(restId);
        int count = 0;
        int sum = 0;
        for (User user : restaurant.getUsers()) {
            for (Cheque cheque : user.getCheques()) {
                if(cheque.getCreatedAt().equals(localDate)){
                    sum = sum + cheque.getPriceAvg().intValue();
                    count++;
                }
            }
        }
        return sum / count;
    }

    @Override
    @Transactional
    public ChequeResponse save(Principal waiterId, List<Long> menuItemIds) {
        Cheque cheque = new Cheque();
        int priceAVG = 0;
        for (MenuItem menuItem : menuItemRepository.findAllById(menuItemIds)) {
            priceAVG = priceAVG + menuItem.getPrice().intValue();
        };
        cheque.setPriceAvg(BigDecimal.valueOf(priceAVG));
        User user = userRepository.getByEmail(waiterId.getName());
        user.getCheques().add(cheque);
        cheque.setUser(user);
        cheque.addMenuItem(menuItemRepository.findAllById(menuItemIds));
        chequeRepository.save(cheque);
        for (MenuItem menuItem : menuItemRepository.findAllById(menuItemIds)) {
            menuItem.addCheque(cheque);
        }

        return ChequeResponse.builder()
                .fullName(user.getFirstName() +" "+ user.getLastName())
                .item(menuItemRepository.findAllById(menuItemIds))
                .avgPrice(BigDecimal.valueOf(priceAVG))
                .servicePercent(priceAVG/100*12)
                .grandTotal(BigDecimal.valueOf(priceAVG + (priceAVG/100*12)))
                .build();
    }
}
