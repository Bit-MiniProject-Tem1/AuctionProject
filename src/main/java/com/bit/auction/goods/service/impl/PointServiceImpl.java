package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Point;
import com.bit.auction.goods.repository.PointRepository;
import com.bit.auction.goods.service.PointService;
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
    public PointDTO getPoint(String userId){
        Optional<Point> optionalPoint = pointRepository.findByUserId(userId);
        return optionalPoint.map(Point::toDTO).orElse(null);
    }

}
