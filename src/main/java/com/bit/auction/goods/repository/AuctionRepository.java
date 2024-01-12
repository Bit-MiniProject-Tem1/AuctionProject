package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @Query(value = "select a from Auction a where a.category.id = :categoryId")
    Page<Auction> findByCategoryId(Pageable pageable, Long categoryId);

    Page<Auction> findByIdIn(Pageable pageable, Long[] subCategoryId);

    // topcategory_id가 category_id이고 subcategorylist에 포함 안되는것만
    //@Query(value = "select a from Auction a where a.category.topCategoryId = :categoryId and a.category.id not in :subCategoryId")
    @Query(value = "select a from Auction a where a.category.id = :categoryId and a.category.topCategoryId is null")
    Page<Auction> findByCategoryIdIsNull(Pageable pageable, Long categoryId);
}
