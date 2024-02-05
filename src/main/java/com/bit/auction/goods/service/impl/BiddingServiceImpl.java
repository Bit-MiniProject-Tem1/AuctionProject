package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.goods.repository.BiddingRepository;
import com.bit.auction.goods.service.BiddingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
    @Override
    public void setAuctionStatus(Long id) {
        biddingRepository.updateStatusByCancel(id);
    }
    @Override
    public BiddingDTO getbid(Long auctionId, String userId){
        Optional<Bidding> optionalBidding = biddingRepository.findByAuctionIdAndUserId(auctionId , userId);
        return optionalBidding.map(Bidding::toDTO).orElse(null);
    }

    @Override
    public List<BiddingDTO> getbidone(String userId){
        List<Bidding> List = biddingRepository.findByUserId(userId);
        return List.stream()
                .map(Bidding::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AuctionDTO> getMyBiddingList(Pageable pageable, String userId) {

        Page<Auction> auctionPageList = biddingRepository.searchMyBiddingList(pageable,userId);
        Page<AuctionDTO> auctionDTOPageList = auctionPageList.map(auction -> auction.toDTO());

        return auctionDTOPageList;
    }

}
