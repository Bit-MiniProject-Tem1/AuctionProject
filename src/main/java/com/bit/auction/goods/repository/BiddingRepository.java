package com.bit.auction.goods.repository;

import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {

    @Modifying
    @Query("update Bidding b set b.status = 0  where b.status =  1 ")
    void updateBidStatus();

    Optional<Bidding> findByAuctionIdAndUserId (Long auctoinId , String userId);
}
