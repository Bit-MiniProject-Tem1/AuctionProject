package com.bit.auction.goods.service;


import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface BiddingService {

    void setbid(BiddingDTO biddingDTO);
    void updateBidStatus();

    BiddingDTO getbid(Long auctionId, String userId);

}
