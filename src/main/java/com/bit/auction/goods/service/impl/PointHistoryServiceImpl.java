package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.PointHistoryDTO;
import com.bit.auction.goods.entity.PointHistory;
import com.bit.auction.goods.repository.PointHistoryRepository;
import com.bit.auction.goods.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public void pointHistoryJoin(PointHistoryDTO pointHistoryDTO) {
        PointHistory pointHistory = pointHistoryDTO.toEntity();

        pointHistoryRepository.save(pointHistory);
    }

    @Override
    public void setPointHistory(int point, String userId, char status){
        pointHistoryRepository.setPointHistory(point, userId, status);
    }

    @Override
    public List<PointHistory> getPointHistory(String userId){
        return pointHistoryRepository.getPointHistory(userId);
    }
}
