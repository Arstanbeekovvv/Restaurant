package restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.ChequeRequest;
import restaurant.dto.response.*;
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
import java.util.stream.Collectors;

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

    @Override
    public ChequePagination findAllCheques(int page, int size, Principal principal) {
        User user = userRepository.getByEmail(principal.getName());
        Long resId = user.getRestaurant().getId();
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Cheque> cheques = chequeRepository.getAllChequesByRestaurantId(resId, pageable);

        List<ChequeResponses> collected = cheques.getContent().stream()
                .map(this::convertToCheque)
                .collect(Collectors.toList());
        return ChequePagination.builder()
                .page(cheques.getNumber() + 1)
                .size(cheques.getNumberOfElements())
                .responses(collected)
                .build();


    }

    @Override
    public ChequeResponses findById(Long chequeId, Principal principal) {
        User user = userRepository.getByEmail(principal.getName());
        List<MenuItemsResponseForCheque> forCheques = chequeRepository.convertToMenu(chequeId);

        Cheque cheque = chequeRepository.getChequeById(chequeId);
        BigDecimal price = sumPrice(cheque.getMenuItems());
        Restaurant userRestaurant = cheque.getUser().getRestaurant();
        int servicePercent = userRestaurant.getService();

        BigDecimal serviceAmount = price.multiply(BigDecimal.valueOf(servicePercent / 100.0));
        BigDecimal grandTotalPrice = price.add(serviceAmount);
        return ChequeResponses.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .responses(forCheques)
                .priceAvg(String.valueOf(price) + " som")
                .service(String.valueOf(userRestaurant.getService() + " %"))
                .totalSum(String.valueOf(grandTotalPrice) +" som")
                .createdAt(cheque.getCreatedAt())
                .build();
    }

    private ChequeResponses convertToCheque(Cheque cheque) {
        User user = cheque.getUser();
        Restaurant restaurant = user.getRestaurant();
        String service = String.valueOf(restaurant.getService());
        int servicePercent = restaurant.getService();
        BigDecimal totalPrice = sumPrice(cheque.getMenuItems());

        BigDecimal serviceAmount = totalPrice.multiply(BigDecimal.valueOf(servicePercent / 100.0));
        BigDecimal grandTotalPrice = totalPrice.add(serviceAmount);

        List<MenuItemsResponseForCheque> collected = cheque.getMenuItems().stream()
                .map(menuItem -> new MenuItemsResponseForCheque(menuItem.getName(),
                        menuItem.getImage(), String.valueOf(menuItem.getPrice())+" som",
                        menuItem.getDescription(), menuItem.isVegetarian()
                ))
                .collect(Collectors.toList());
        return new ChequeResponses(
                user.getFirstName() + " " + user.getLastName(), collected,
                String.valueOf(totalPrice) +" som",
                service + " %", String.valueOf(grandTotalPrice)+" som", cheque.getCreatedAt()
        );
    }
    private BigDecimal sumPrice(List<MenuItem> menuItems) {
        return menuItems.stream()
                .map(MenuItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
