package restaurant.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
@Builder
public record ChequePagination( int page,
                                int size,
                                List<ChequeResponses> responses) {
}
