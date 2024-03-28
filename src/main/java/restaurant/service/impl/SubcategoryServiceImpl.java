package restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.CatSaveRequest;
import restaurant.dto.response.*;
import restaurant.entities.*;
import restaurant.exceptions.AlreadyExistsException;
import restaurant.exceptions.NotFoundException;
import restaurant.repository.CategoryRepository;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.SubcategoryRepository;
import restaurant.repository.UserRepository;
import restaurant.service.SubcategoryService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;

    private final SubcategoryRepository subCategoryRepo;
    private final CategoryRepository categoryRepo;
    private final RestaurantRepository restaurantRepo;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse saveSub(Long catId, CatSaveRequest catSaveRequest, Principal principal) {
        return null;
    }

    @Override
    public SubCategoriesPagination findAllSUbCategories(Long catId, Principal principal, int page, int size) {
        return null;
    }

    @Override
    public SubCategoriesResponse findById(Long subId, Principal principal) {
        return null;
    }

    @Override
    public SimpleResponse update(Long subId, Principal principal, CatSaveRequest catSaveRequest) {
        return null;
    }

    @Override
    public SimpleResponse delete(Long subId, Principal principal) {
        return null;
    }

    @Override
    public List<SubCategoriesResponseFilter> filterWithCategory(Principal principal) {
        return null;
    }

//    private void checkName(String name) {
//        boolean b = subCategoryRepo.existsByName(name);
//        if (b) throw new AlreadyExistsException("Category with name: " + name + " already have");
//    }
//    private void checkSubId(Long subId){
//        subCategoryRepo.getReferenceById(subId);
//    }
//
//    @Override
//    @Transactional
//    public SimpleResponse saveSub(Long catId, CatSaveRequest catSaveRequest, Principal principal) {
//        checkName(catSaveRequest.name());
//        Category category = categoryRepo.getReferenceById(catId);
//        Subcategory subCategory = new Subcategory();
//        subCategory.setName(catSaveRequest.name());
//
//        category.addSubcategory(subCategory);
//        subCategory.setCategory(category);
//        subcategoryRepository.save(subCategory);
//
//        return SimpleResponse.builder()
//                .httpStatus(HttpStatus.OK)
//                .message("Sub Category with name: "+subCategory.getName()+" successfully saved")
//                .build();
//    }
//
//    @Override
//    public SubCategoriesPagination findAllSUbCategories(Long catId, Principal principal, int page, int size) {
//        Pageable pageable = PageRequest.of(page - 1, size);
//        Page<Subcategory> subCategories = subCategoryRepo.findSubCategoriesById(catId, pageable);
//
//        if (subCategories.isEmpty()) throw new NotFoundException("Sub categories not found");
//
//        List<CategoriesResponse> collect = subCategories.getContent().stream()
//                .map(this::convertToSub)
//                .collect(Collectors.toList());
//
//        Comparator<CategoriesResponse> nameComparator = Comparator
//                .comparing(categoriesResponse -> categoriesResponse.name().toLowerCase());
//
//        collect.sort(nameComparator);
//        return SubCategoriesPagination.builder()
//                .page(subCategories.getNumber() + 1)
//                .size(subCategories.getNumberOfElements())
//                .responses(collect)
//                .build();
//    }
//
//    @Override
//    public SubCategoriesResponse findById(Long subId, Principal principal) {
//        Subcategory subCategory = subCategoryRepo.getSubCategoryId(subId);
//        List<MenuItemsResponse> collect = subCategory.getMenuItems().stream()
//                .map(this::convertToMenu)
//                .collect(Collectors.toList());
//
//        return SubCategoriesResponse.builder()
//                .id(subCategory.getId())
//                .name(subCategory.getName())
//                .responses(collect)
//                .build();
//    }
//
//    @Override
//    public SimpleResponse update(Long subId, Principal principal, CatSaveRequest catSaveRequest) {
//        checkSubId(subId);
//        checkName(catSaveRequest.name());
//
//        Subcategory subCategory = subCategoryRepo.getSubCategoryId(subId);
//
//        subCategory.setName(catSaveRequest.name());
//        subCategoryRepo.save(subCategory);
//
//        return SimpleResponse.builder()
//                .httpStatus(HttpStatus.OK)
//                .message("Sub category successfully updated")
//                .build();
//    }
//
//    @Override
//    public SimpleResponse delete(Long subId, Principal principal) {
//        checkSubId(subId);
//        Subcategory subCategory = subCategoryRepo.getSubCategoryId(subId);
//        subCategoryRepo.delete(subCategory);
//        return SimpleResponse.builder()
//                .httpStatus(HttpStatus.OK)
//                .message("Sub category successfully deleted")
//                .build();
//    }
//
//    @Override
//    public List<SubCategoriesResponseFilter> filterWithCategory(Principal principal) {
//        User user = userRepository.getByEmail(principal.getName());
//        Long resId = user.getRestaurant().getId();
//        List<Category> categories = categoryRepo.findAllCategories(resId);
//
//        Map<String, List<CategoriesResponse>> groupedResponses = new HashMap<>();
//
//        for (Category category : categories) {
//            groupedResponses.put(category.getName(), new ArrayList<>());
//        }
//
//        List<Subcategory> subCategories = subCategoryRepo.findAllSubCatById(resId);
//
//        for (Subcategory subCategory : subCategories) {
//            CategoriesResponse categoriesResponse = convertToSub(subCategory);
//            String categoryName = subCategory.getCategory().getName();
//            groupedResponses.get(categoryName).add(categoriesResponse);
//        }
//
//        return groupedResponses.entrySet().stream()
//                .map(entry -> new SubCategoriesResponseFilter(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//    }
//
//    private MenuItemsResponse convertToMenu(MenuItem menuItem) {
//        return new MenuItemsResponse(
//                menuItem.getId(), menuItem.getName(), menuItem.getImage(),
//                menuItem.getPrice(), menuItem.getDescription(), menuItem.isVegetarian(),
//                754
//        );
//    }
//
//    private CategoriesResponse convertToSub(Subcategory sub) {
//        return new CategoriesResponse(sub.getId(), sub.getName());
//    }
}
