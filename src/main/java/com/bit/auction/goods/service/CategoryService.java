package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO getCategory(Long category_id);

    List<CategoryDTO> getAllCategoryList();

    List<CategoryDTO> getTopCategoryList();

    List<CategoryDTO> searchSubCategoryList(Long categoryId);
}
