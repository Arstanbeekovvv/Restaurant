package restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.response.MenuItemsResponseForCheque;
import restaurant.entities.Cheque;
import restaurant.exceptions.NotFoundException;

import java.util.List;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    default Cheque getChequeById(Long chequeId){
        return findById(chequeId).orElseThrow(() -> new NotFoundException("Not Found! id: " + chequeId));
    };

    default Page<Cheque> getAllChequesByRestaurantId(Long resId, Pageable pageable){
        List<Cheque> cheques = findAllByRestId(resId);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), cheques.size());
        return new PageImpl<>(cheques.subList(start, end), pageable, cheques.size());
    }

    @Query("select c from Cheque c where c.user.restaurant.id = ?1")
    List<Cheque> findAllByRestId(Long resId);
    @Query("""
            select new restaurant.dto.response.MenuItemsResponseForCheque(
            m.name, m.image, m.price, m.description, m.isVegetarian)
            from Cheque c join c.menuItems m where c.id =:chequeId
            """)
    List<MenuItemsResponseForCheque> convertToMenu(Long chequeId);
}
