package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.AuctionImg;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuctionImgDTO {
    private Long id;
    private Long auctionId; // fk
    private String fileUrl;
    private String fileName;
    private boolean isRepresentative;

    public AuctionImg toEntity(Auction auction) {
        return AuctionImg.builder()
                .id(this.id)
                .auction(auction)
                .fileUrl(this.fileUrl)
                .fileName(this.fileName)
                .isRepresentative(this.isRepresentative)
                .build();
    }
}
