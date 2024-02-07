package com.bit.auction.goods.service;


import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.entity.Bidding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BiddingService {

    void setbid(BiddingDTO biddingDTO);
    void updateBidStatus(Long auctionId);

    void setAuctionStatus(Long id);

    BiddingDTO getbid(Long auctionId, String userId);
    List<BiddingDTO> findByAuctionId(Long categoryId);
    List<BiddingDTO> getbidone(String userId);
    List<AuctionDTO> getMyBiddingList(String userId);
}
