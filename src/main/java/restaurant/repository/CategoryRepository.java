package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.request.CategoryRequest;
import restaurant.entities.Category;
import restaurant.entities.Restaurant;
import restaurant.exceptions.NotFoundException;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String nameCategory);

    default Category getBybId(Long categoryId){
        return findById(categoryId).orElseThrow(()-> new NotFoundException("Not Found category by this id: " + categoryId));
    };

    @Query("select c from Restaurant r join r.menuItems m join m.subcategory.category c")
    List<Category> getAllCategory(Long id);

    @Query("select c from Category c join c.subcategories s join s.menuItems mi where mi.restaurant.id=:resId")
    List<Category> findAllCategories(Long resId);

}
