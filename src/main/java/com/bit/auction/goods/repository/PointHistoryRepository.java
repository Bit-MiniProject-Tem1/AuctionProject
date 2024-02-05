package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.PointHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into point_history(point, user_id, status, modified_date) VALUES(:point, :userId, :status , now())" , nativeQuery = true)
    void setPointHistory(int point, String userId, char status);

    @Query(value = "select P from PointHistory P where P.userId = :userId ")
    List<PointHistory> getPointHistory(String userId);

}
