package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "bidding")
public class Bidding {

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "auction_id")
private Auction auctionId;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "user_id")
private User userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long biddingId;

    private int biddingPrice;

    private int status;

    private int payment;

    private LocalDateTime date;

    public BiddingDTO toDTO(){
        return BiddingDTO.builder()
                .auctionId(this.auctionId.getId())
                .userId(this.userId.getUserId())
                .biddingPrice(this.biddingPrice)
                .date(this.date)
                .status(this.status)
                .build();
    }


}

