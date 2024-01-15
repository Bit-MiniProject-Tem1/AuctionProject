package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.BiddingRequestDTO;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.goods.repository.BiddingRepository;
import com.bit.auction.goods.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BiddingServiceImpl implements BiddingService {

    private final BiddingRepository biddingRepository;

    @Override
    public void biddingPrice(BiddingRequestDTO biddingRequestDTO) {

        Bidding bidding = biddingRequestDTO.toEntity();

        biddingRepository.save(bidding);
    }
}
