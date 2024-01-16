package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.DescriptionImg;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DescriptionImgDTO {
    private Long id;
    private Long auctionId; // fk
    private String fileUrl;
    private String fileName;

    public DescriptionImg toEntity(Auction auction) {
        return DescriptionImg.builder()
                .id(this.id)
                .auction(auction)
                .fileUrl(this.fileUrl)
                .fileName(this.fileName)
                .build();
    }
}
