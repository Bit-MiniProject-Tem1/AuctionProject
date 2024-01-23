package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.Bidding;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BiddingRequestDTO {

    private String userId;

    private Long auctionId;

    private int biddingPrice;

    private boolean status;

    private int payment;


    public Bidding toEntity() {
        return Bidding.builder()
                .userId(userId)
                .auctionId(auctionId)
                .biddingPrice(biddingPrice)
                .status(status)
                .build();
    }
}
