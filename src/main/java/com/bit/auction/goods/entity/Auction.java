package com.bit.auction.goods.entity;

import com.bit.auction.goods.dto.AuctionDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long regUserId; // fk

    // private Long categoryId; // fk
    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @NotNull
    private String title;

    @NotNull
    @Column(name = "goods_description")
    private String description;

    private String target;

    @NotNull
    @Column(columnDefinition = "default 'S'")
    private char status;

    @NotNull
    private int startingPrice;

    private int currentBiddingPrice;

    @NotNull
    private int immediate_price;

    @CreationTimestamp
    //@Column(columnDefinition = "datetime(6) default now(6)", updatable = false, nullable = false)
    private LocalDateTime regDate;

    @NotNull
    @Column(columnDefinition = "datetime(6)")
    private LocalDateTime endDate;

    private String successfulBidderId; // fk 의현님 구현 부분

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AuctionImg> auctionImgList;
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DescriptionImg> descriptionImgList;

    public AuctionDTO toDTO() {
        return AuctionDTO.builder()
                .id(this.id)
                .regUserId(this.regUserId)
                .categoryName(this.category.getName())
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .startingPrice(this.startingPrice)
                .currentBiddingPrice(this.currentBiddingPrice)
                .immediate_price(this.immediate_price)
                .regDate(this.regDate)
                .endDate(this.endDate)
                .successfulBidderId(this.successfulBidderId)
                .auctionImgDTOList(this.auctionImgList.stream().map(AuctionImg::toDTO).toList())
                .descriptionImgDTOList(this.descriptionImgList.stream().map(DescriptionImg::toDTO).toList())
                .build();
    }

    public void addAuctionImgList(AuctionImg auctionImg) {
        this.auctionImgList.add(auctionImg);
    }

    public void addDescriptionImgList(DescriptionImg descriptionImg) {
        this.descriptionImgList.add(descriptionImg);
    }

}
