package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.ChequeRequest;
import restaurant.dto.response.ChequePagination;
import restaurant.dto.response.ChequeResponse;
import restaurant.dto.response.ChequeResponses;
import restaurant.dto.response.SimpleResponse;
import restaurant.service.ChequeService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cheque")
public class ChequeAPI {
    private final ChequeService chequeService;

    @Secured("ADMIN")
    @GetMapping("/sum/{waiterId}")
    public Integer sumCheque(@PathVariable Long waiterId, @RequestBody LocalDate localDate) {
        return chequeService.sum(waiterId, localDate);
    }

    @Secured("ADMIN")
    @GetMapping("/avg-rest/{restId}")
    public Integer avgChequeForOneDay(@PathVariable Long restId,
                                      @RequestBody LocalDate localDate) {
        return chequeService.avgCheque(restId, localDate);
    }

    //save
    @Secured({"ADMIN", "WAITER"})
    @PostMapping("/save")
    public ChequeResponse createCheque(Principal principal,
                                       @RequestBody List<Long> menuItemIds) {
        return chequeService.save(principal, menuItemIds);
    }
    // update (admin)
    @Secured("ADMIN")
    @PutMapping("/update/{chequeId}")
    public SimpleResponse update(@PathVariable Long chequeId,
                                 @RequestBody ChequeRequest chequeRequest) {
        return chequeService.update(chequeId, chequeRequest);
    }

    // delete (admin)
    @Secured("ADMIN")
    @DeleteMapping("/delete/{chequeId}")
    public SimpleResponse delete(@PathVariable Long chequeId) {
        return chequeService.delete(chequeId);
    }

    @Secured({"ADMIN", "WAITER", "CHEF"})
    @GetMapping
    public ChequePagination findAll(Principal principal,
                                    @RequestParam int page,
                                    @RequestParam int size){
        return chequeService.findAllCheques(page, size, principal);
    }

    @Secured({"ADMIN", "WAITER", "CHEF"})
    @GetMapping("/find/{chequeId}")
    public ChequeResponses findById(@PathVariable Long chequeId, Principal principal){
        return chequeService.findById(chequeId, principal);
    }
}
