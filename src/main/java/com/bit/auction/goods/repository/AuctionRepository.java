package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
    // @Query(value = "select a from Auction a where a.category.id = :categoryId and a.status in :status")
    // Page<Auction> findBySubCategoryId(Pageable pageable, Long categoryId, List<Character> status);

    // @Query(value = "select a from Auction a where a.category.id in :subCategoryId and a.status in :status")
    // Page<Auction> findByTopCategoryId(Pageable pageable, List<Long> subCategoryId, List<Character> status);
}
