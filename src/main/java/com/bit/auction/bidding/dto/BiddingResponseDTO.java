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
@Setter

public class BiddingResponseDTO {

    private Long bidderId;

    private String userId;

    private Long auctionId;

    private int biddingPrice;

    private boolean status;

    private LocalDate date;

}
