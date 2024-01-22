package com.bit.auction.goods.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DescriptionImgDTO {
    private Long id;
    private Long auctionId;
    private String fileUrl;
    private String fileName;
}