package com.bit.auction.goods.repository;

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

//    @Modifying
//    @Query("update Auction a set a.likeCnt = a.likeCnt + 1 where a.id = :id")
//    void plusLikeCnt(LikeCntDTO likeCntDTO);
//    @Modifying
//    @Query("update Auction a set a.likeCnt = a.likeCnt - 1 where a.id = :id")
//    void minusLikeCnt(LikeCntDTO likeCntDTO);
}
