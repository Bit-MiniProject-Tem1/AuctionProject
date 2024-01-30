package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.LikeCntDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.LikeCnt;
import com.bit.auction.goods.repository.AuctionImgRepository;
import com.bit.auction.goods.repository.AuctionRepository;
import com.bit.auction.goods.repository.LikeCntRepository;
import com.bit.auction.goods.service.LikeCntService;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeCntServiceImpl implements LikeCntService {

    private final AuctionRepository auctionRepository;
    private final LikeCntRepository likeCntRepository;

    @Override
    public long findByUserIdAndAuctionId(long userId, Long auctionId) {

        return likeCntRepository.countByUserIdAndAuctionId(userId, auctionId);
    }

    @Override
    @Modifying
    public void insertLike(User user, Long categoryId) {
        Auction auction = auctionRepository.findById(categoryId).orElseThrow();

        LikeCnt likeCnt = LikeCnt.builder()
                        .user(user)
                                .auction(auction)
                                        .build();

        likeCntRepository.save(likeCnt);
        likeCntRepository.flush();
    }

    @Override
    @Modifying
    public void deleteLike(User user, Long categoryId) {
        Auction auction = auctionRepository.findById(categoryId).orElseThrow();

        LikeCnt likeCnt = LikeCnt.builder()
                .user(user)
                .auction(auction)
                .build();

        likeCntRepository.delete(likeCnt);
        likeCntRepository.flush();
    }

    @Override
    public long findByAuctionId(Long auctionId) {

        return likeCntRepository.countByAuctionId(auctionId);
    }
}
