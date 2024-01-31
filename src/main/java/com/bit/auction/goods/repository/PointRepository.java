package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByUserId(String userId);
}
