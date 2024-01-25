package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.goods.repository.AuctionRepository;
import com.bit.auction.goods.repository.BiddingRepository;
import com.bit.auction.goods.service.BiddingService;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BiddingServiceImpl implements BiddingService {

    private final BiddingRepository biddingRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Override
    public Long insertBid(BiddingDTO biddingDTO){

        Bidding bidding = dtoToEntity(biddingDTO);

        biddingRepository.save(bidding);

        return bidding.getBiddingId();
    }
    @Override
    public List<BiddingDTO> getBidList(Long auctionId){
        List<Bidding> result = biddingRepository
                .getBiddingByAuction(Auction.builder()
                        .id(auctionId)
                        .build());

        return result.stream().map(bidding -> entityToDTO(bidding))
                .collect(Collectors.toList());
    }

    @Override
    public List<BiddingDTO> getUserBidList(Long userId){
        List<Bidding> result = biddingRepository
                .getBiddingByUser(User.builder()
                        .id(userId)
                        .build());

        return result.stream().map(bidding -> entityToDTO(bidding))
                .collect(Collectors.toList());
    }

}
