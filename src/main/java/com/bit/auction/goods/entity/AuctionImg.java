package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.AuctionImgDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_img_id")
    @JsonBackReference
    private Long id;
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;
    private String fileUrl;
    private String fileName;
    private boolean isRepresentative;

    public AuctionImgDTO toDTO() {
        return AuctionImgDTO.builder()
                .id(this.id)
                .auctionId(this.auction.getId())
                .fileUrl(this.fileUrl)
                .fileName(this.fileName)
                .isRepresentative(this.isRepresentative)
                .build();
    }
}
