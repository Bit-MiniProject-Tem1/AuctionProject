package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BiddingRepository extends JpaRepository<Bidding, Long>  {

    @Modifying
    @Query("update Bidding b set b.status = 'l'  where b.status =  'u' and b.auctionId = :auctionId")
    void updateBidStatus(Long auctionId);

    @Modifying
    @Query("update Bidding b set b.auctionStatus = 'C' where b.auctionId = :id")
    void updateStatusByCancel(Long id);


    Optional<Bidding> findByAuctionIdAndUserId (Long auctionId , String userId);

@Query("SELECT b " +
        "FROM Bidding b " +
        "WHERE b.userId = :userId " +
        "AND (b.auctionId, b.date) IN ( " +
        "    SELECT bb.auctionId, MAX(bb.date) " +
        "    FROM Bidding bb " +
        "    WHERE bb.userId = :userId " +
        "    GROUP BY bb.auctionId " +
        ")")

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

@Query("SELECT COUNT(DISTINCT b.auctionId) " +
        "FROM Bidding b " +
        "WHERE b.auctionStatus = 'S' " +
        "AND b.userId = :userId")
  Long countAuctionStatusS(String userId);
  @Query("SELECT COUNT(b) FROM Bidding b WHERE b.auctionStatus = 'E' and b.userId = :userId")
  Long countAuctionStatusE(String userId);
  @Query("SELECT COUNT(b) FROM Bidding b WHERE b.auctionStatus = 'C' and b.userId = :userId")
  Long countAuctionStatusC(String userId);

  @Query("SELECT b FROM Bidding b WHERE b.auctionId = :auctionId ORDER BY b.date DESC")
  List<Bidding> findByAuctionId(Long auctionId);


}
