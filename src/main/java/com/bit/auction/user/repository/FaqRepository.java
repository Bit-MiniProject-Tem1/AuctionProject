package com.bit.auction.user.repository;

import com.bit.auction.user.entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
//
//    public Page<Faq> test(Pageable pageable, String categoty, String searchKeyword);
//    @Query(value = "SELECT * FROM your_entity_table WHERE your_condition", nativeQuery = true)
//    Page<Faq> findAllByCategoryAfterAndTitleContainingOrContentContaining(Pageable pageable);
//

    // 검색조건 : 카테고리
    Page<Faq> findByCategory(Pageable pageable, String category);

    // 검색조건 : 제목
    Page<Faq> findByTitleContaining(Pageable pageable, String title);

    // 검색조건 : 내용
    Page<Faq> findByContentContaining(Pageable pageable, String content);

    // 검색조건 : 제목, 내용
    Page<Faq> findByTitleContainingOrContentContaining(Pageable pageable, String title, String content);

    // 검색조건 : 카테고리, 제목
    Page<Faq> findByCategoryAndTitleContaining(Pageable pageable, String category, String title);

    // 검색조건 : 카테고리, 내용
    Page<Faq> findByCategoryAndContentContaining(Pageable pageable, String category, String content);

    // 검색조건 : 카테고리, 제목, 내용
    Page<Faq> findByCategoryAndTitleContainingOrContentContaining(Pageable pageable, String category, String title, String content);

    Page<Faq> findByCategoryAndTitleContainingOrCategoryAndContentContaining(Pageable pageable, String category1, String title, String category2, String content);
}
