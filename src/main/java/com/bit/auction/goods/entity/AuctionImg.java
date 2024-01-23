package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.AuctionImgDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
//@Setter
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

    public void setRepresentative(boolean isRepresentative) {
        this.isRepresentative = isRepresentative;
    }
}
