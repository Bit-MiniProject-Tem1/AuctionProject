package com.bit.auction.goods.dto;

import lombok.Getter;
import lombok.Setter;

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
