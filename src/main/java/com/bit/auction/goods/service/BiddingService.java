package com.bit.auction.goods.service;


import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BiddingService {

    Long insertBid(BiddingDTO biddingDTO);
    List<BiddingDTO> getBidList(Long auctionId);
    List<BiddingDTO> getUserBidList(Long userId);

    default Bidding dtoToEntity(BiddingDTO biddingDTO){
        Auction auction = Auction.builder().id(biddingDTO.getAuctionId()).build();
        User user = User.builder().id(biddingDTO.getUserId()).build();
        Bidding bidding = Bidding.builder()
                .auction(auction)
                .user(user)
                .biddingId((biddingDTO.getBiddingId()))
                .biddingPrice(biddingDTO.getBiddingPrice())
                .payment(biddingDTO.getPayment())
                .date(LocalDateTime.now())
                .status(biddingDTO.getStatus())
                .build();

        return bidding;
    }

    default BiddingDTO entityToDTO(Bidding bidding){
        BiddingDTO biddingDTO = BiddingDTO.builder()
                .biddingId(bidding.getBiddingId())
                .biddingPrice(bidding.getBiddingPrice())
                .date(LocalDateTime.now())
                .status(bidding.getStatus())
                .build();
        return  biddingDTO;
    }
}
