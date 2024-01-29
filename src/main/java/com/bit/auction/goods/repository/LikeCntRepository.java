package com.bit.auction.goods.repository;

import com.bit.auction.goods.dto.LikeCntDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.LikeCnt;
import com.bit.auction.goods.entity.LikeCntId;
import com.bit.auction.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface LikeCntRepository  extends JpaRepository<LikeCnt, LikeCntId> {
    long countByUserIdAndAuctionId(long userId, Long categoryId);

    long countByAuctionId(Long categoryId);
}
