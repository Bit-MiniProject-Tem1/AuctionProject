package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BiddingDTO {

    private Long biddingId;

    private int biddingPrice;

    private int payment;

    private LocalDateTime date;

    private int status;

    //fk
    private Long auctionId;

    private String userId;


    public Bidding toEntity(Auction auctionId , User userId) {
        return Bidding.builder()
                .auctionId(auctionId)
                .userId(userId)
                .biddingPrice(this.biddingPrice)
                .payment(this.payment)
                .date(LocalDateTime.now())
                .build();
    }
}
