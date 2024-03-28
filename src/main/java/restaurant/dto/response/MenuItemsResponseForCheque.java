package restaurant.dto.response;

import lombok.Builder;

@Builder
public record MenuItemsResponseForCheque(
        String name,
        String image,
        String price,
        String description,
        boolean isVegetarian
)
{}
