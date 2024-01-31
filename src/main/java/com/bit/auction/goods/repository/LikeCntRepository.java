package com.bit.auction.goods.repository;

import com.bit.auction.goods.dto.LikeCntDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.LikeCnt;
import com.bit.auction.goods.entity.LikeCntId;
import com.bit.auction.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

@Transactional
public interface LikeCntRepository  extends JpaRepository<LikeCnt, LikeCntId> {
    long countByUserIdAndAuctionId(long userId, Long categoryId);

    long countByAuctionId(Long categoryId);

    @Query(value = "SELECT A.AUCTION_ID as AUCTION_ID" +
            "            , IFNULL(COUNT(B.AUCTION_ID), 0) as LIKE_SUM" +
            "           FROM AUCTION A" +
            "           LEFT OUTER JOIN LIKE_CNT B" +
            "           ON A.AUCTION_ID = B.AUCTION_ID" +
            "           GROUP BY A.AUCTION_ID",
        nativeQuery = true)
    List<Map<String, Long>> countGroupByAuctionId();

    @Query(value = "SELECT A.AUCTION_ID as AUCTION_ID" +
            "            , B.USER_ID as USER_ID" +
            "            , IFNULL(COUNT(B.AUCTION_ID), 0) as LIKE_SUM" +
            "           FROM AUCTION A" +
            "           LEFT OUTER JOIN LIKE_CNT B" +
            "           ON A.AUCTION_ID = B.AUCTION_ID" +
            "           GROUP BY A.AUCTION_ID, B.USER_ID" +
            "           HAVING B.USER_ID = :id",
            nativeQuery = true)
    List<Map<String, Long>> countGroupByAuctionIdUserId(long id);
}
