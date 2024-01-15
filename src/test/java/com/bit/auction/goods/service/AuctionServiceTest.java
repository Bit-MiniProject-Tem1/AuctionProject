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
        Category category1 = categoryRepository.findById(1L).get();
        Auction auction1 = Auction.builder()
                .regUserId("han")
                .category(category1)
                .title("테스트 제목1")
                .description("테스트 내용")
                .startingPrice(50000)
                .immediatePrice(700000)
                .endDate(LocalDateTime.parse("2024-05-17T13:40:00.000000"))
                .build();
        auctionRepository.saveAndFlush(auction1);

        Category category2 = categoryRepository.findById(2L).get();
        Auction auction2 = Auction.builder()
                .regUserId("park")
                .category(category2)
                .title("테스트 제목2")
                .description("테스트 내용")
                .target("unisex")
                .startingPrice(210000)
                .immediatePrice(500000)
                .endDate(LocalDateTime.parse("2024-01-17T13:40:00.000000"))
                .build();
        auctionRepository.saveAndFlush(auction2);

        Category category3 = categoryRepository.findById(3L).get();
        Auction auction3 = Auction.builder()
                .regUserId("choi")
                .category(category3)
                .title("테스트 제목3")
                .description("테스트 내용")
                .target("baby")
                .startingPrice(30000)
                .immediatePrice(70000)
                .endDate(LocalDateTime.parse("2024-11-17T13:40:00.000000"))
                .build();
        auctionRepository.saveAndFlush(auction3);

        Category category4 = categoryRepository.findById(4L).get();
        Auction auction4 = Auction.builder()
                .regUserId("han")
                .category(category4)
                .title("테스트 제목4")
                .description("테스트 내용")
                .target("female")
                .status('E')
                .successfulBidderId("choi")
                .startingPrice(15000)
                .immediatePrice(30000)
                .endDate(LocalDateTime.parse("2024-05-17T13:40:00.000000"))
                .build();
        auctionRepository.saveAndFlush(auction4);
        log.info(">>>" + auctionRepository.findAll());

        Category category5 = categoryRepository.findById(5L).get();
        Auction auction5 = Auction.builder()
                .regUserId("han")
                .category(category5)
                .title("테스트 제목4")
                .description("테스트 내용")
                .target("female")
                .status('C')
                .startingPrice(15000)
                .immediatePrice(30000)
                .endDate(LocalDateTime.parse("2024-05-17T13:40:00.000000"))
                .build();
        auctionRepository.saveAndFlush(auction5);
        log.info(">>>" + auctionRepository.findAll());
    }

}