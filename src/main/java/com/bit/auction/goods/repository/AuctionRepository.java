package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
