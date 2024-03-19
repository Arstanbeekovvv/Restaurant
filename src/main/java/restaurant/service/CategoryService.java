package restaurant.service;

import restaurant.dto.request.CategoryRequest;
import restaurant.dto.response.SimpleResponse;

import java.util.List;

public interface CategoryService {
    SimpleResponse save(String nameCategory);

    CategoryRequest findById(Long categoryId);

    List<CategoryRequest> findAll();

    SimpleResponse update(Long categoryId, String newNameCategory);

    SimpleResponse delete(Long categoryId);

}
