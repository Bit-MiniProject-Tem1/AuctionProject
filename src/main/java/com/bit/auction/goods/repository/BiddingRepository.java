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

    Optional<Bidding> findByAuctionIdAndUserId (Long auctionId , String userId);
    List<Bidding> findByUserId (String userId);

    @Query("SELECT a,b FROM Auction a JOIN Bidding b ON a.id = b.auctionId AND b.userId = :userId")
//    @Query(value = "SELECT a.* , b.* FROM Auction AS a JOIN Bidding AS b ON a.auction_id = b.auction_id AND b.user_id = :userId" , nativeQuery = true)
    Page<Auction> searchMyBiddingList(Pageable pageable, String userId);
}
