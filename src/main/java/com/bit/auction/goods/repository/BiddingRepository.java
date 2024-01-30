package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface BiddingRepository extends JpaRepository<Bidding, Long> {

    @Modifying
    @Query("update Bidding b set b.status = 0  where b.status =  1 ")
    void updateBidStatus();

}
