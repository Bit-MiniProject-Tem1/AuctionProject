package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.dto.PointHistoryDTO;
import com.bit.auction.goods.entity.PointHistory;

import java.util.List;

public interface PointHistoryService {

    void pointHistoryJoin (PointHistoryDTO pointHistoryDTO);

    void setPointHistory(int point, String userId, char status);

    List<PointHistory> getPointHistory(String userId);
}
