package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select c.name from Category c where c.id = :categoryId")
    public String findNameByCategoryId(Long categoryId);

    @Query(value = "select c2 from Category c1 right join Category c2 on c1.id = c2.topCategoryId")
    public List<Category> findCategory();

    public List<Category> findByTopCategoryIdIsNull();

    @Query(value = "select c2 from Category c1 right join Category c2 on c1.id = c2.topCategoryId where c2.topCategoryId = :categoryId")
    public List<Category> findSubCategoryList(Long categoryId);

    @Query(value = "select c2.id from Category c1 right join Category c2 on c1.id = c2.topCategoryId where c2.topCategoryId = :categoryId")
    public Long[] findSubCategoryIdList(Long categoryId);
}
