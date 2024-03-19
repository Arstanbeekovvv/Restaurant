package restaurant.dto.response;

import lombok.Builder;
import restaurant.entities.MenuItem;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ChequeResponse(String fullName,
                             List<MenuItem> item,
                             BigDecimal avgPrice,
                             int servicePercent,
                             BigDecimal grandTotal
)
{}
