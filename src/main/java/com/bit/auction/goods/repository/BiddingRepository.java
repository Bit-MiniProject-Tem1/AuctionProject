package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BiddingRepository extends JpaRepository<Bidding, Long>  {

    @Modifying
    @Query("update Bidding b set b.status = 'l'  where b.status =  'u' ")
    void updateBidStatus();

    @Modifying
    @Query(value = "update Bidding b set b.auctionStatus='C' where b.auctionId = :id")
    void updateStatusByCancel(Long id);

    @Query
    Optional<Bidding> findByAuctionIdAndUserId (Long auctionId , String userId);
    List<Bidding> findByUserId (String userId);

    @Query("SELECT a, b " +
            "FROM Auction a " +
            "JOIN Bidding b ON a.id = b.auctionId " +
            "WHERE (a.id, b.date) IN (" +
            "    SELECT a.id, MAX(b.date) " +
            "    FROM Auction a " +
            "    JOIN Bidding b ON a.id = b.auctionId " +
            "    WHERE b.userId = :userId " +
            "    GROUP BY a.id" +
            ") " +
            "ORDER BY b.date DESC")
    List<Auction> searchMyBiddingList(String userId);

}
