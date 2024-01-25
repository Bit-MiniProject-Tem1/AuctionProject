package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {

    @Query(value = "SELECT a FROM Auction a ORDER BY a.regDate DESC")
    List<Auction> findByforResent();
    @Query(value = "SELECT a FROM Auction a ORDER BY a.endDate ASC")
    List<Auction> findByforFinal();
    @Query(value = "SELECT a FROM Auction a WHERE LOWER(a.title) LIKE LOWER(concat('%', :searchQuery, '%')) AND a.status IN :statusList ORDER BY a.regDate DESC")
    List<Auction> findByAuctionNameContaining(String searchQuery, List<Character> statusList);
}
