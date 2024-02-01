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

//@ManyToOne(cascade = CascadeType.ALL)
//@JoinColumn(name = "auction_id")
//private Auction auction;
//
//@ManyToOne(cascade = CascadeType.ALL)
//@JoinColumn(name = "user_id")
//private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long biddingId;

    private int biddingPrice;

    private int status;

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
                .date(LocalDateTime.now())
                .build();
    }


}

