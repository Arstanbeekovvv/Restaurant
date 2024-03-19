package restaurant.dto.request;

import restaurant.validation.Price;

import java.math.BigDecimal;

public record MenuItemRequest(String name,
                              String image,
                              @Price
                              BigDecimal price,
                              String description,
                              boolean isVegetarian
) {
}
