package com.bit.auction.goods.dto;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuctionDTO {
    private Long id;
    private String regUserId; // fk
    private Long categoryId; // fk
    private String categoryName;
    private String title;
    private String description;
    private String target;
    private char status;
    private int startingPrice;
    private int currentBiddingPrice;
    private int immediatePrice;
    private LocalDateTime regDate;
    private LocalDateTime endDate;
    private String successfulBidderId; // fk
    private int view;
    private List<AuctionImgDTO> auctionImgDTOList;
    private String representativeImgUrl;
    private String representativeImgName;
    private List<Long> deleteAuctionImgList;
    //  private List<DescriptionImgDTO> descriptionImgDTOList;

    public Auction toEntity(Category category) {
        return Auction.builder()
                .id(this.id)
                .regUserId(this.regUserId)
                .category(category)
                .title(this.title)
                .description(this.description)
                .target(this.target)
                .status(this.status)
                .startingPrice(this.startingPrice)
                .currentBiddingPrice(this.currentBiddingPrice)
                .immediatePrice(this.immediatePrice)
                .regDate(this.regDate)
                .endDate(this.endDate)
                .successfulBidderId(this.successfulBidderId)
                .representativeImgUrl(this.representativeImgUrl)
                .representativeImgName(this.representativeImgName)
                .view(this.view)
                .auctionImgList(new ArrayList<>())
                //   .descriptionImgList(new ArrayList<>())
                .build();
    }

}
