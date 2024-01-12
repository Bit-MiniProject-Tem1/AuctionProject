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
}
