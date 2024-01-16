package com.bit.auction.goods.controller;

import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getSubCategory")
    public List<CategoryDTO> getSubCategory(Long topCategoryId) {

        return categoryService.searchSubCategoryList(topCategoryId);
    }
}
