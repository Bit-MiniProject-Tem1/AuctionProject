package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.BiddingDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "bidding")
public class Bidding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long biddingId;

    private int biddingPrice;

    private char status;

    private char auctionStatus;

    private int payment;

    private LocalDateTime date;

    private Long auctionId;

    private String userId;

    public BiddingDTO toDTO(){
        return BiddingDTO.builder()
                .auctionId(auctionId)
                .userId(userId)
                .biddingPrice(biddingPrice)
                .payment(payment)
                .status(status)
                .auctionStatus(auctionStatus)
                .date(date)
                .build();
    }

}

