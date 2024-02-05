package com.bit.auction.admin.repository;

import com.bit.auction.goods.entity.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberInfo_BiddingRepository extends JpaRepository<Bidding, Long> {
    List<Bidding> findByUserId(String userId);
}
