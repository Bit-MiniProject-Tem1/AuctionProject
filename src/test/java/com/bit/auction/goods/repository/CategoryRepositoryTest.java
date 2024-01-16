package com.bit.auction.goods.repository;

import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.entity.Category;
import com.bit.auction.goods.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
class CategoryRepositoryTest {
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
        categoryRepository.findCategory().forEach(category -> {
            log.info("category_id : " + category.getId() + ", name : " + category.getName());
        });
    }

    @DisplayName("CategoryDTO 조회 테스트")
    @Test
    void getCategoryAllDTO() {
        List<Category> categoryList = categoryRepository.findCategory();

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

    @DisplayName("Category 테이블 insert 테스트")
    @Test
    void insertCategory() {
        // topCategory
        Category category1 = Category.builder()
                .name("아우터")
                .build();
        Category category2 = Category.builder()
                .name("상의")
                .build();
        Category category3 = Category.builder()
                .name("하의")
                .build();
        Category category4 = Category.builder()
                .name("신발")
                .build();
        Category category5 = Category.builder()
                .name("가방")
                .build();
        Category category6 = Category.builder()
                .name("시계")
                .build();
        Category category7 = Category.builder()
                .name("패션잡화")
                .build();
        List<Category> categoryList = new ArrayList<>(Arrays.asList(category1, category2, category3, category4, category5, category6, category7));
        categoryRepository.saveAllAndFlush(categoryList);
    }

    @DisplayName("Category 테이블 insert 테스트")
    @Test
    void insertSubCategory() {
        // subCategory
        List<Category> categoryList;

        Category category1 = Category.builder()
                .name("자켓")
                .topCategoryId(1L)
                .build();
        Category category2 = Category.builder()
                .name("아노락")
                .topCategoryId(1L)
                .build();
        Category category3 = Category.builder()
                .name("코트")
                .topCategoryId(1L)
                .build();
        Category category4 = Category.builder()
                .name("패딩")
                .topCategoryId(1L)
                .build();
        categoryList = Arrays.asList(category1, category2, category3, category4);
        categoryRepository.saveAllAndFlush(categoryList);

        Category category5 = Category.builder()
                .name("티셔츠")
                .topCategoryId(2L)
                .build();
        Category category6 = Category.builder()
                .name("가디건")
                .topCategoryId(2L)
                .build();
        Category category7 = Category.builder()
                .name("셔츠")
                .topCategoryId(2L)
                .build();
        Category category8 = Category.builder()
                .name("후드")
                .topCategoryId(2L)
                .build();
        Category category9 = Category.builder()
                .name("스웨트셔츠")
                .topCategoryId(2L)
                .build();
        Category category10 = Category.builder()
                .name("원피스")
                .topCategoryId(2L)
                .build();
        Category category11 = Category.builder()
                .name("니트")
                .topCategoryId(2L)
                .build();
        categoryList = new ArrayList<>(Arrays.asList(category5, category6, category7, category8, category9, category10, category11));
        categoryRepository.saveAllAndFlush(categoryList);

        Category category12 = Category.builder()
                .name("바지")
                .topCategoryId(3L)
                .build();
        Category category13 = Category.builder()
                .name("반바지")
                .topCategoryId(3L)
                .build();
        Category category14 = Category.builder()
                .name("스커트")
                .topCategoryId(3L)
                .build();
        Category category15 = Category.builder()
                .name("레깅스")
                .topCategoryId(3L)
                .build();
        categoryList = new ArrayList<>(Arrays.asList(category12, category13, category14, category15));
        categoryRepository.saveAllAndFlush(categoryList);

        Category category16 = Category.builder()
                .name("스니커즈")
                .topCategoryId(4L)
                .build();
        Category category17 = Category.builder()
                .name("샌들/슬리퍼")
                .topCategoryId(4L)
                .build();
        Category category18 = Category.builder()
                .name("플랫")
                .topCategoryId(4L)
                .build();
        Category category19 = Category.builder()
                .name("로퍼")
                .topCategoryId(4L)
                .build();
        Category category20 = Category.builder()
                .name("힐")
                .topCategoryId(4L)
                .build();
        Category category21 = Category.builder()
                .name("부츠")
                .topCategoryId(4L)
                .build();
        categoryList = new ArrayList<>(Arrays.asList(category16, category17, category18, category19, category20, category21));
        categoryRepository.saveAllAndFlush(categoryList);

        Category category22 = Category.builder()
                .name("미니백")
                .topCategoryId(5L)
                .build();
        Category category23 = Category.builder()
                .name("백팩")
                .topCategoryId(5L)
                .build();
        Category category24 = Category.builder()
                .name("숄더백")
                .topCategoryId(5L)
                .build();
        Category category25 = Category.builder()
                .name("토트백")
                .topCategoryId(5L)
                .build();
        Category category26 = Category.builder()
                .name("크로스백")
                .topCategoryId(5L)
                .build();
        Category category27 = Category.builder()
                .name("에코백")
                .topCategoryId(5L)
                .build();
        Category category28 = Category.builder()
                .name("캐리어")
                .topCategoryId(5L)
                .build();
        categoryList = new ArrayList<>(Arrays.asList(category22, category23, category24, category25, category26, category27, category28));
        categoryRepository.saveAllAndFlush(categoryList);

        Category category29 = Category.builder()
                .name("프리미엄 시계")
                .topCategoryId(6L)
                .build();
        Category category30 = Category.builder()
                .name("전자 시계")
                .topCategoryId(6L)
                .build();
        Category category31 = Category.builder()
                .name("가죽 시계")
                .topCategoryId(6L)
                .build();
        Category category32 = Category.builder()
                .name("메탈 시계")
                .topCategoryId(6L)
                .build();
        categoryList = new ArrayList<>(Arrays.asList(category29, category30, category31, category32));
        categoryRepository.saveAllAndFlush(categoryList);

        Category category33 = Category.builder()
                .name("주얼리")
                .topCategoryId(7L)
                .build();
        Category category34 = Category.builder()
                .name("아이웨어")
                .topCategoryId(7L)
                .build();
        Category category35 = Category.builder()
                .name("머플러/스카프")
                .topCategoryId(7L)
                .build();
        Category category36 = Category.builder()
                .name("모자")
                .topCategoryId(7L)
                .build();
        Category category37 = Category.builder()
                .name("벨트")
                .topCategoryId(7L)
                .build();
        Category category38 = Category.builder()
                .name("양말")
                .topCategoryId(7L)
                .build();
        Category category39 = Category.builder()
                .name("장갑")
                .topCategoryId(7L)
                .build();
        Category category40 = Category.builder()
                .name("키링")
                .topCategoryId(7L)
                .build();
        categoryList = new ArrayList<>(Arrays.asList(category33, category34, category35, category36, category37, category38, category39, category40));
        categoryRepository.saveAllAndFlush(categoryList);
    }
}