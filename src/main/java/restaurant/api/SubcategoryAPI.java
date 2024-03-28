package restaurant.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.CatSaveRequest;
import restaurant.dto.response.SimpleResponse;
import restaurant.dto.response.SubCategoriesPagination;
import restaurant.dto.response.SubCategoriesResponse;
import restaurant.dto.response.SubCategoriesResponseFilter;
import restaurant.service.SubcategoryService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subcategory")
public class SubcategoryAPI {
    private final SubcategoryService subcategoryService;
//
//    @Secured({"ADMIN", "CHEF", "WAITER"})
//    @GetMapping("/{catId}")
//    public SubCategoriesPagination findAllByCatId(@PathVariable Long catId,
//                                                  Principal principal,
//                                                  @RequestParam int page,
//                                                  @RequestParam int size){
//        return subcategoryService.findAllSUbCategories(catId, principal, page, size);
//    }
//
//    @Secured({"ADMIN", "CHEF"})
//    @PostMapping("/{catId}")
//    public SimpleResponse saveSUb(@PathVariable Long catId,
//                                  @RequestBody @Valid CatSaveRequest catSaveRequest,
//                                  Principal principal){
//        return subcategoryService.saveSub(catId, catSaveRequest, principal);
//    }
//
//    @Secured({"ADMIN", "CHEF", "WAITER"})
//    @GetMapping("/find/{subId}")
//    public SubCategoriesResponse findById(@PathVariable Long subId, Principal principal){
//        return subcategoryService.findById(subId, principal);
//    }
//
//    @Secured({"ADMIN", "CHEF"})
//    @PutMapping("/{subId}")
//    public SimpleResponse update(@PathVariable Long subId, Principal principal,
//                                 @RequestBody @Valid CatSaveRequest catSaveRequest){
//        return subcategoryService.update(subId, principal, catSaveRequest);
//    }
//
//    @Secured({"ADMIN", "CHEF"})
//    @DeleteMapping("/{subId}")
//    public SimpleResponse delete(@PathVariable Long subId, Principal principal){
//        return subcategoryService.delete(subId, principal);
//    }
//
//    @Secured({"ADMIN", "CHEF", "WAITER"})
//    @GetMapping("/filter")
//    public List<SubCategoriesResponseFilter> filterWithCategory(Principal principal){
//        return subcategoryService.filterWithCategory(principal);
//    }
}
