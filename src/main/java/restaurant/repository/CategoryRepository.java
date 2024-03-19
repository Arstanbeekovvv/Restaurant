package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.dto.request.CategoryRequest;
import restaurant.entities.Category;
import restaurant.exceptions.NotFoundException;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String nameCategory);

    default Category getBybId(Long categoryId){
        return findById(categoryId).orElseThrow(()-> new NotFoundException("Not Found category by this id: " + categoryId));
    };
}
