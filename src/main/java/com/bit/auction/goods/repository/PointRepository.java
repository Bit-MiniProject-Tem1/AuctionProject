package com.bit.auction.goods.repository;

import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.entity.Point;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

        Optional<Point> findPointByUserId(String userId);

        @Modifying
        @Transactional
        @Query("update Point P set P.point = P.point + :point  where P.userId = :userId ")
        void pointCharge(int point , String userId);

        @Modifying
        @Transactional
        @Query("update Point P set P.point = P.point - :point  where P.userId = :userId ")
        void pointWithdraw(int point , String userId);

}
