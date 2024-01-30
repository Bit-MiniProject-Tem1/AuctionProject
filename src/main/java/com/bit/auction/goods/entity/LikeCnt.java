package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.LikeCntDTO;
import com.bit.auction.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likeCnt")
@AllArgsConstructor
@Builder
@IdClass(LikeCntId.class)
public class LikeCnt {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public LikeCntDTO toDTO() {
            return LikeCntDTO.builder()
                    .auction(this.auction.getId())
                    .user(this.user.getId())
                    .build();
        }

}
