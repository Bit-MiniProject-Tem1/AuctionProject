package com.bit.auction.bidding.repository;

import com.bit.auction.bidding.entity.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {

}
