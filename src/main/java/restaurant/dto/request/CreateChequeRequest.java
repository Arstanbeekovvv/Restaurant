package restaurant.dto.request;

import java.util.List;

public record CreateChequeRequest(List<Long> menuItemIds) {
}
