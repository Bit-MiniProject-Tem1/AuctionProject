package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.AuctionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuctionService {
    Page<AuctionDTO> getAuctionList(Pageable pageable, Long categoryId, String filter, List<String> target, List<Character> status);

    void insertAuction(AuctionDTO auctionDTO);

    List<AuctionDTO> searchAuctions(String searchQuery, List<Character> status);

    List<AuctionDTO> findByForRecentList();

    List<AuctionDTO> findByForFinalList();
}
