package com.bit.auction.admin.repository;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionInfoRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAll(Specification<Auction> specification);
}
