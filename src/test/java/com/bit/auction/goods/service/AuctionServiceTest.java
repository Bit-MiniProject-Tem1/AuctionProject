package com.bit.auction.goods.service;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Category;
import com.bit.auction.goods.repository.AuctionRepository;
import com.bit.auction.goods.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
class AuctionServiceTest {
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @DisplayName("Auction 테이블 insert 테스트")
    @Test
    void insertAuction() {
        Category category = categoryRepository.findById(1L).get();
        Auction auction = Auction.builder()
                .regUserId(1L)
                .category(category)
                .title("테스트 제목")
                .description("테스트 내용")
                .target("여성")
                .startingPrice(50000)
                .immediate_price(700000)
                .endDate(LocalDateTime.parse("2024-11-27T16:44:00.000000"))
                .build();
        auctionRepository.save(auction);
        log.info(">>>" + auctionRepository.findAll());
    }
}