package restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.CategoryRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.MenuResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.entities.Category;
import restaurant.entities.MenuItem;
import restaurant.exceptions.BadRequestException;
import restaurant.repository.CategoryRepository;
import restaurant.repository.UserRepository;
import restaurant.service.CategoryService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse save(String nameCategory) {
        if (categoryRepository.existsByName(nameCategory))
            throw new BadRequestException("This category already exists! ");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved category! ")
                .build();
    }

    @Override
    public CategoryRequest findById(Long categoryId) {
        return CategoryRequest.builder()
                .id(categoryId)
                .name(categoryRepository.getBybId(categoryId).getName())
                .build();
    }

    @Override
    public List<CategoryRequest> findAll() {
        List<CategoryRequest> categoryRequests = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            categoryRequests.add(CategoryRequest.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build());
        }
        return categoryRequests;
    }

    @Override
    @Transactional
    public SimpleResponse update(Long categoryId, String newNameCategory) {
        Category category = categoryRepository.getBybId(categoryId);
        category.setName(newNameCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated category name!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long categoryId) {
        categoryRepository.delete(categoryRepository.getBybId(categoryId));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted!")
                .build();
    }

    @Override
    public List<CategoryResponse> globalSearch(Principal principal, String globalSearch) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        List<Category> categories = categoryRepository.getAllCategory(userRepository.getByEmail(principal.getName()).getRestaurant().getId());
        for (Category category : categories) {
            if(category.getName().contains(globalSearch)){
                categoryResponses.add(CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build());

            }
        }
        return categoryResponses;
    }
}
