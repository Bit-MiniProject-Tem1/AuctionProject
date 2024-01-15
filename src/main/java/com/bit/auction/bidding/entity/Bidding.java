package com.bit.auction.bidding.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bidding")
public class Bidding {

//    @Id
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "auction_id")
//    private Auction auction;

//    @Id
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidderId;

    private String userId;

    private Long auctionId;

    private int biddingPrice;

    private boolean status;

    private LocalDateTime date = LocalDateTime.now();

    @Builder
    public Bidding(Long bidderId, String userId, Long auctionId, int biddingPrice, boolean status) {
        this.bidderId = bidderId;
        this.userId = userId;
        this.auctionId = auctionId;
        this.biddingPrice = biddingPrice;
        this.status = status;
    }
}

