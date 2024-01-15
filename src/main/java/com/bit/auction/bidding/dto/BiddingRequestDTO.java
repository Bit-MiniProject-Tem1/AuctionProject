package com.bit.auction.bidding.dto;

import com.bit.auction.bidding.entity.Bidding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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


    public Bidding toEntity() {
        return Bidding.builder()
                .userId(userId)
                .auctionId(auctionId)
                .biddingPrice(biddingPrice)
                .status(status)
                .build();
    }
}
