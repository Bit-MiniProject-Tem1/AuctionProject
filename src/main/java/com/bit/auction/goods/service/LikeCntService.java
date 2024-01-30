package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.LikeCntDTO;
import com.bit.auction.user.entity.User;

public interface LikeCntService {
    long findByUserIdAndAuctionId(long userId, Long categoryId);
    void insertLike(User user, Long categoryId);
    void deleteLike(User user, Long categoryId);
    long findByAuctionId(Long categoryId);


}
