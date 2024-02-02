package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AuctionService {
    AuctionDTO getAuctionGoods(Long id);

    List<Long> getSubCategoryIdList(Long categoryId);

    Page<AuctionDTO> getAuctionList(Pageable pageable, Long categoryId, String sortOption, List<String> target, List<Character> status);

    Page<AuctionDTO> getMyAuctionList(Pageable pageable, String regUserId, String status);
    Page<AuctionDTO> getMyBiddingList(Pageable pageable, String userId);

    void setAuction(AuctionDTO auctionDTO, Long categoryId, User user);

    void removeDescriptionImg(String description, String originDescription, List<String> temporaryImageList);

    void removeDescriptionImg(List<String> temporaryImageList);

    void updateView(Long id, HttpServletRequest request, HttpServletResponse response);

    void cancelAuction(Long id);

    Page<AuctionDTO> searchAuctions(Pageable pageable, String searchQuery, List<Character> status);

    List<AuctionDTO> findByForRecentList();

    List<AuctionDTO> findByForFinalList();

    List<Map<String, Long>> getLikeSumList();

    List<Map<String, Long>> getUserLikeList(long id);

    List<AuctionDTO> findByForPopularList();

}
