package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.AuctionImg;

public interface AuctionImgRepositoryCustom {
    void saveOne(AuctionImg auctionImg);

    void updaterepresentativeImg(Auction auction);
}
