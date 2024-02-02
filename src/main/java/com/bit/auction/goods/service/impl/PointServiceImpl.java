package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Point;
import com.bit.auction.goods.repository.PointRepository;
import com.bit.auction.goods.service.PointService;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public void pointJoin(PointDTO pointDTO){

        Point point = pointDTO.toEntity();

        pointRepository.save(point);

    }
    @Override
    public PointDTO getPoint(String userId) {
        Optional<Point> optionalPoint = pointRepository.findPointByUserId(userId);
        return optionalPoint.map(Point::toDTO).orElse(null);
    }
    @Override
    public void pointCharge(int point, String userId) {
        pointRepository.pointCharge(point, userId);
    }
        @Override
    public void pointWithdraw(int point, String userId) {
        pointRepository.pointWithdraw(point, userId);
    }
}
