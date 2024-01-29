package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.LikeCnt;
import com.bit.auction.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LikeCntDTO {
    private Long user;
    private Long auction;



    public LikeCnt toEntity() {

            User user = User.builder()
                    .id(this.user)
                    .build();

            Auction auction = Auction.builder()
                                     .id(this.auction)
                                     .build();

              return LikeCnt.builder()
                    .user(user)
                    .auction(auction)
                    .build();
    }
}
