package com.example.datn.services.ServiceImpl;

import com.example.datn.dto.Category.CategoryDto;
import com.example.datn.entities.Category;
import com.example.datn.exceptions.ShopApiException;
import com.example.datn.repositories.CategoryRepo;
import com.example.datn.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Page<Category> getAllCategory(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }

    @Override
    public Category createCategory(Category category) {
        if(categoryRepo.existsByCode(category.getCode())) {
            throw new ShopApiException(HttpStatus.BAD_REQUEST, "Mã loại " + category.getCode() + " đã tồn tại");
        }
        category.setDeleteFlag(false);
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category existingCategory = categoryRepo.findById(category.getId()).orElseThrow(null);
        if(!existingCategory.getCode().equals(category.getCode())) {
            if(categoryRepo.existsByCode(category.getCode())) {
                throw new ShopApiException(HttpStatus.BAD_REQUEST, "Mã loại " + category.getCode() + " đã tồn tại");
            }
        }
        category.setDeleteFlag(false);
        return categoryRepo.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(null);
        category.setDeleteFlag(true);
        categoryRepo.save(category);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepo.existsById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepo.findById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public CategoryDto createCategoryApi(CategoryDto categoryDto) {
        if(categoryRepo.existsByCode(categoryDto.getCode())) {
            throw new ShopApiException(HttpStatus.BAD_REQUEST, "Mã loại đã tồn tại");
        }
        Category category = new Category(null, categoryDto.getCode(), categoryDto.getName(), 1, false);
        Category categoryNew = categoryRepo.save(category);
        return new CategoryDto(category.getId(), category.getCode(), category.getName());
    }
}
