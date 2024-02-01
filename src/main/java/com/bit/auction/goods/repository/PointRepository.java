package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

        Optional<Point> findPointByUserId(String userId);
}
