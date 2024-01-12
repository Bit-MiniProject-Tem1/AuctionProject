package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.entity.Category;
import com.bit.auction.goods.repository.CategoryRepository;
import com.bit.auction.goods.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public String getCategoryName(Long categoryId) {
        return categoryRepository.findNameByCategoryId(categoryId);
    }

    @Override
    public CategoryDTO getCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new RuntimeException("data not exist");
        }
        return optionalCategory.get().toDTO();
    }

    @Override
    public List<CategoryDTO> getAllCategoryList() {
        List<Category> categoryList = categoryRepository.findCategory();

        return categoryList.stream().map(Category::toDTO).toList();
    }

    @Override
    public List<CategoryDTO> getTopCategoryList() {
        List<Category> categoryList = categoryRepository.findByTopCategoryIdIsNull();

        return categoryList.stream().map(Category::toDTO).toList();
    }

    @Override
    public List<CategoryDTO> searchSubCategoryList(Long categoryId) {
        List<Category> categoryList = categoryRepository.findSubCategoryList(categoryId);

        return categoryList.stream().map(Category::toDTO).toList();
    }
}
