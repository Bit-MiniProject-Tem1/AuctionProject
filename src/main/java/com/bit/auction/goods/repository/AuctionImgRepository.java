package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.AuctionImg;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface AuctionImgRepository extends JpaRepository<AuctionImg, Long>, AuctionImgRepositoryCustom {
}
