package restaurant.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChequeRequest(BigDecimal priceAvg,
                            LocalDate createdAt) {
}
