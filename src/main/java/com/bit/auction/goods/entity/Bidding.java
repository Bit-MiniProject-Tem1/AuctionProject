package com.bit.auction.goods.entity;

import com.bit.auction.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bidding")
public class Bidding {

@Id
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "auction_id")
private Auction auction;

//@Id
//@ManyToOne(fetch = FetchType.LAZY)
//@JoinColumn(name = "user_id")
//private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long biddingId;

    private String userId;

    private int biddingPrice;

    private boolean status;

    private int payment;

    private LocalDateTime date = LocalDateTime.now();

    @Builder
    public Bidding(Long biddingId, String userId, Auction auction, int biddingPrice, boolean status, int payment) {
        this.biddingId = biddingId;
        this.userId = userId;
        this.auction = auction;
        this.biddingPrice = biddingPrice;
        this.status = status;
        this.payment = payment;
    }
}

