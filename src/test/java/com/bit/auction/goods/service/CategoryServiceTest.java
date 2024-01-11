package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.entity.Category;
import com.bit.auction.goods.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("Category 테이블 조회 테스트")
    @Test
    void getCategory() {
        categoryRepository.findAll().forEach(category -> {
            log.info("category_id : " + category.getId() + ", name : " + category.getName());
        });
    }

    @DisplayName("Category 테이블 상속 조회 테스트")
    @Test
    void getCategoryAll() {
        categoryRepository.findByCategory().forEach(category -> {
            log.info("category_id : " + category.getId() + ", name : " + category.getName());
        });
    }

    @DisplayName("CategoryDTO 조회 테스트")
    @Test
    void getCategoryAllDTO() {
        List<Category> categoryList = categoryRepository.findByCategory();

        List<CategoryDTO> categoryDTOList = categoryList.stream().map(Category::toDTO).toList();
        categoryDTOList.forEach(categoryDTO -> {
            log.info("category_id : " + categoryDTO.getId() + ", name : " + categoryDTO.getName());
        });
    }

    @DisplayName("최상위 CategoryDTO 조회 테스트")
    @Test
    void getTopCategoryAll() {
        List<Category> categoryList = categoryRepository.findByTopCategoryIdIsNull();

        List<CategoryDTO> categoryDTOList = categoryList.stream().map(Category::toDTO).toList();
        categoryDTOList.forEach(categoryDTO -> {
            log.info("category_id : " + categoryDTO.getId() + ", name : " + categoryDTO.getName());
        });
    }
}