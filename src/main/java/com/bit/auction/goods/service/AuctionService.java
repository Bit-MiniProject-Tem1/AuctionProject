package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.AuctionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuctionService {
    AuctionDTO getAuctionGoods(Long id);

    Page<AuctionDTO> getAuctionList(Pageable pageable, Long categoryId, String filter, List<String> target, List<Character> status);

    Page<AuctionDTO> getMyAuctionList(Pageable pageable, String regUserId, String status);

    void insertAuction(AuctionDTO auctionDTO, Long categoryId);

    void updateAuction(AuctionDTO auctionDTO, Long categoryId);

    void removeDescriptionImg(String description, String originDescription, List<String> temporaryImageList);

    void removeDescriptionImg(List<String> temporaryImageList);

    List<AuctionDTO> searchAuctions(String searchQuery, List<Character> status);

    List<AuctionDTO> findByForRecentList();

    List<AuctionDTO> findByForFinalList();
}
