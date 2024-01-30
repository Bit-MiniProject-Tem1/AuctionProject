package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.goods.repository.BiddingRepository;
import com.bit.auction.goods.service.BiddingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BiddingServiceImpl implements BiddingService {

    private final BiddingRepository biddingRepository;

    @Override
    public void setbid(BiddingDTO biddingDTO){
        Bidding bidding = biddingDTO.toEntity();
        biddingRepository.save(bidding);

    }

    @Transactional
    @Override
    public void updateBidStatus(){
        biddingRepository.updateBidStatus();
    }

}
