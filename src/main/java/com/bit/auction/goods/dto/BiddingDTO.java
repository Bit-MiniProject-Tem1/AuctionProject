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

    private Long auctionId;

    private Long userId;

    private LocalDateTime date;

    private int status;

//    public Bidding toEntity(Auction auction , User user) {
//        return Bidding.builder()
//                .auction(auction)
//                .user(user)
//                .biddingPrice(this.biddingPrice)
//                .payment(this.payment)
//                .date(this.date)
//                .build();
//    }
}
