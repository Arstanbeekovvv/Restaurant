package restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.entities.Subcategory;
import restaurant.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
//    boolean existsByName(String name);
//    @Query("select s from Subcategory s where s.category.id =:catId")
//    List<Subcategory> findAllSubCategories(Long catId);
//    default Page<Subcategory> findSubCategoriesById(Long catId, Pageable pageable){
//        List<Subcategory> list = findAllSubCategories(catId);
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), list.size());
//        return new PageImpl<>(list.subList(start, end), pageable, list.size());
//    }
//
//    @Query("select s from Subcategory s where s.id =:subId")
//    Optional<Subcategory> findSubCategoryById(Long subId);
//
//    default Subcategory getSubCategoryId(Long subId){
//        return findSubCategoryById(subId).orElseThrow(() ->
//                new NotFoundException("Sub category with id: " + subId + " not found"));
//    }
//
//    @Query("select s from Subcategory s where s.category.restaurant.id=:resId")
//    List<Subcategory> findAllSubCatById(Long resId);


}
