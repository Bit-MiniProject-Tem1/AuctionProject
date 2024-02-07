package com.bit.auction.admin.repository;

import com.bit.auction.goods.entity.Bidding;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiddingInfoRepository extends JpaRepository<Bidding, Long> {
    List<Bidding> findByUserId(String userId);

    List<Bidding> findAll(Specification<Bidding> specification);

    /*List<Bidding> findByDate*/
}
