package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.AuctionDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;

    @Column(nullable = false)
    private String regUserId; // fk

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(name = "goods_description", nullable = false)
    private String description;

    private String target;

    @Column(columnDefinition = "char(1) default 'S'", nullable = false)
    @Builder.Default()
    private char status = 'S';

    @Column(nullable = false)
    private int startingPrice;

    @Column(columnDefinition = "integer default 0", nullable = false)
    @Builder.Default()
    private int currentBiddingPrice = 0;

    @Column(nullable = false)
    private int immediatePrice;

    @CreationTimestamp
    @Column(columnDefinition = "datetime(6)", updatable = false, nullable = false)
    private LocalDateTime regDate;

    @Column(columnDefinition = "datetime(6)", nullable = false)
    private LocalDateTime endDate;

    private String successfulBidderId; // fk 의현님 구현 부분

    @Column(columnDefinition = "integer default 0", nullable = false)
    @Builder.Default()
    private int view = 0;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AuctionImg> auctionImgList;
    // @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    // @JsonManagedReference
    // private List<DescriptionImg> descriptionImgList;

    @Transient
    private String representativeImgUrl;
    @Transient
    private String representativeImgName;

    public AuctionDTO toDTO() {
        auctionImgList.forEach(auctionImg -> {
            if (auctionImg.isRepresentative()) {
                representativeImgUrl = auctionImg.getFileUrl();
                representativeImgName = auctionImg.getFileName();
            }
        });
        return AuctionDTO.builder()
                .id(this.id)
                .regUserId(this.regUserId)
                .categoryId(this.category.getId())
                .categoryName(this.category.getName())
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
                .view(this.view)
                .auctionImgDTOList(this.auctionImgList.stream().map(AuctionImg::toDTO).toList())
                .representativeImgUrl(this.representativeImgUrl)
                .representativeImgName(this.representativeImgName)
                //   .descriptionImgDTOList(this.descriptionImgList.stream().map(DescriptionImg::toDTO).toList())
                .build();
    }

    public void addAuctionImg(AuctionImg auctionImg) {
        this.auctionImgList.add(auctionImg);
    }

    public void representativeImgUrl(String representativeImgUrl) {
        this.representativeImgUrl = representativeImgUrl;
    }

    // public void addDescriptionImgList(DescriptionImg descriptionImg) {
    //     this.descriptionImgList.add(descriptionImg);
    // }
}
