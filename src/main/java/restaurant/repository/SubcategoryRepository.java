package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.entities.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
}
