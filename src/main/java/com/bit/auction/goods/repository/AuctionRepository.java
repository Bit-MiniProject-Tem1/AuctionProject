package com.bit.auction.goods.repository;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.LikeCntDTO;
import com.bit.auction.goods.entity.Auction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDateTime;

@Transactional
public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
    @Modifying(clearAutomatically = true)
    @Query(value = "update Auction a set a.status='E' where a.endDate<:now")
    void updateStatusByEndDate(LocalDateTime now);

    @Modifying
    @Query(value = "update Auction a set a.status='C' where a.id = :id")
    int updateStatusByCancel(Long id);

    @Modifying
    @Query("update Auction a set a.view = a.view + 1 where a.id = :id")
    int updateView(Long id);
    @Query(value = "SELECT a FROM Auction a ORDER BY a.regDate DESC")
    List<Auction> findByforResent();
    @Query(value = "SELECT a FROM Auction a ORDER BY a.endDate ASC")
    List<Auction> findByforFinal();

    @Query(value = "SELECT a FROM Auction a WHERE LOWER(a.title) LIKE LOWER(concat('%', :searchQuery, '%')) AND a.status IN :statusList ORDER BY a.regDate DESC")
    Page<Auction> findByAuctionNameContaining(Pageable pageable, String searchQuery, List<Character> statusList);

    @Query(value = "SELECT a FROM Auction a LEFT JOIN LikeCnt b on a.id = b.auction.id GROUP BY a.id ORDER BY COALESCE(count(b.auction.id), 0) DESC")
    List<Auction> countGroupByAuctionId();

    @Query(value = "SELECT A, B FROM Auction A join LikeCnt B ON A.id = B.auction.id WHERE B.user.id = :id")
    List<Auction> findByUserId(long id);

    @Modifying
    @Query("update Auction a set a.currentBiddingPrice = :BiddingPrice  where a.id = :auctionId ")
    void updateCurrentBiddingPrice(Long auctionId, int BiddingPrice);
}
