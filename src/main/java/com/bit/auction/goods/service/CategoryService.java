package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    String getCategoryName(Long categoryId);

    CategoryDTO getCategory(Long categoryId);

    List<CategoryDTO> getAllCategoryList();

    List<CategoryDTO> getTopCategoryList();

    List<CategoryDTO> searchSubCategoryList(Long categoryId);
}
