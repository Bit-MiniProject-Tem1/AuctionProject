package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.DescriptionImgDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "GOODS_DESCRIPTION_IMG")
public class DescriptionImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_discription_img_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction; // fk
    private String fileUrl;
    private String fileName;

    public DescriptionImgDTO toDTO() {
        return DescriptionImgDTO.builder()
                .id(this.id)
                .auctionId(this.auction.getId())
                .fileUrl(this.fileUrl)
                .fileName(this.fileName)
                .build();
    }
}
