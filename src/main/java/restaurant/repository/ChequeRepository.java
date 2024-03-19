package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.entities.Cheque;
import restaurant.exceptions.NotFoundException;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    default Cheque getChequeById(Long chequeId){
        return findById(chequeId).orElseThrow(() -> new NotFoundException("Not Found! id: " + chequeId));
    };
}
