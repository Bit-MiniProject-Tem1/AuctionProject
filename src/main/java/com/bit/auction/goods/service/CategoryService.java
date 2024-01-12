package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    String getCategoryName(Long categoryId);

    List<CategoryDTO> getTopCategoryList();

    List<CategoryDTO> searchSubCategoryList(Long categoryId);
}
