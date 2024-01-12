package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.entity.Category;
import com.bit.auction.goods.repository.CategoryRepository;
import com.bit.auction.goods.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public String getCategoryName(Long categoryId) {
        return categoryRepository.findNameByCategoryId(categoryId);
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
