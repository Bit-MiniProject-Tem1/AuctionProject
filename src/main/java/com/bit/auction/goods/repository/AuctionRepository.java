package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

@Transactional
public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
    @Modifying(clearAutomatically = true)
    @Query(value = "update Auction a set a.status='E' where a.endDate<:now")
    void updateStatusByEndDate(LocalDateTime now);

    @Modifying
    @Query("update Auction a set a.view = a.view + 1 where a.id = :id")
    int updateView(Long id);
}
