package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.CategoryRequest;
import restaurant.dto.response.SimpleResponse;
import restaurant.entities.Category;
import restaurant.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryAPI {
    private final CategoryService categoryService;

//********************************************************************************
    //      ** CRUD **
    // save
    @Secured("ADMIN")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody String nameCategory){
        return categoryService.save(nameCategory);
    }

    // find by id
    @Secured({"ADMIN", "WAITER"})
    @GetMapping("/get/{categoryId}")
    public CategoryRequest findById(@PathVariable Long categoryId){
        return categoryService.findById(categoryId);
    }

    // find all
    @Secured({"ADMIN", "WAITER"})
    @GetMapping("/find-all")
    public List<CategoryRequest> findAll(){
        return categoryService.findAll();
    }
    // update
    @Secured({"ADMIN"})
    @PutMapping("/update/{categoryId}")
    public SimpleResponse update(@PathVariable Long categoryId,
                                 @RequestBody String newNameCategory){
        return categoryService.update(categoryId, newNameCategory);
    }
    // delete
    @Secured({"ADMIN"})
    @DeleteMapping("/delete/{categoryId}")
    public SimpleResponse delete(@PathVariable Long categoryId){
        return categoryService.delete(categoryId);
    }
//********************************************************************************
}
