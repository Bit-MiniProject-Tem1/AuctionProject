package com.bit.auction.bidding.service.impl;

import com.bit.auction.bidding.dto.BiddingRequestDTO;
import com.bit.auction.bidding.entity.Bidding;
import com.bit.auction.bidding.repository.BiddingRepository;
import com.bit.auction.bidding.service.BiddingService;
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
