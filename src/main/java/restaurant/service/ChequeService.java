package restaurant.service;

import restaurant.dto.request.ChequeRequest;
import restaurant.dto.response.ChequePagination;
import restaurant.dto.response.ChequeResponse;
import restaurant.dto.response.ChequeResponses;
import restaurant.dto.response.SimpleResponse;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface ChequeService {
    Integer sum(Long waiterId, LocalDate localDate);

    SimpleResponse update(Long chequeId, ChequeRequest chequeRequest);

    SimpleResponse delete(Long chequeId);

    Integer avgCheque(Long restId, LocalDate localDate);

    ChequeResponse save(Principal principal, List<Long> menuItemIds);

    ChequePagination findAllCheques(int page, int size, Principal principal);

    ChequeResponses findById(Long chequeId, Principal principal);
}
