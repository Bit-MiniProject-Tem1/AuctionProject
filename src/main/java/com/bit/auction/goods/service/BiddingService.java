package com.bit.auction.goods.service;


import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BiddingService {

    void setbid(BiddingDTO biddingDTO , Auction auctionId , User userId);
//    List<BiddingDTO> getBidList(Long auctionId);
//    List<BiddingDTO> getUserBidList(Long userId);

}
