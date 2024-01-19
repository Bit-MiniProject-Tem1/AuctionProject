package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuctionRepositoryCustom {
    Page<Auction> searchAll(Pageable pageable, Long categoryId, List<Long> subCategoryIdList, List<String> targetList, List<Character> statusList);

    void saveOne(Auction auction);

    List<Auction> findByAuctionNameContaining(String searchQuery, List<Character> statusList);

    List<Auction> findByforResent();

    List<Auction> findByforFinal();
}
