package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.PointDTO;

public interface PointService {
    void pointJoin (PointDTO pointDTO);

    PointDTO getPoint(String userId);
}
