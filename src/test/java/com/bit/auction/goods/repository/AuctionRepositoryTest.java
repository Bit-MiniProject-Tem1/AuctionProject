package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Category;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
class AuctionRepositoryTest {
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("Auction 테이블 insert 테스트")
    @Test
    void insertAuction1() {
        Category category1 = categoryRepository.findById(1L).get();
        User user = userRepository.findByUserId("kim").get();
        Auction auction1 = Auction.builder()
                .regUser(user)
                .category(category1)
                .title("테스트 제목1")
                .description("테스트 내용")
                .startingPrice(50000)
                .immediatePrice(700000)
                .endDate(LocalDateTime.parse("2024-01-23T09:40:00.000000"))
                .build();
        auctionRepository.saveAndFlush(auction1);
    }

    @DisplayName("Auction 테이블 insert 테스트")
    @Test
    void insertAuction() {
        Category category1 = categoryRepository.findById(1L).get();
        User user = userRepository.findByUserId("test").get();
        Auction auction1 = Auction.builder()
                .regUser(user)
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
                .regUser(user)
                .category(category2)
                .title("테스트 제목2")
                .description("테스트 내용")
                .target("unisex")
                .startingPrice(210000)
                .immediatePrice(500000)
                .endDate(LocalDateTime.parse("2024-01-17T13:40:00.000000"))
                .build();
        auctionRepository.saveAndFlush(auction2);

        Category category3 = categoryRepository.findById(8L).get();
        Auction auction3 = Auction.builder()
                .regUser(user)
                .category(category3)
                .title("테스트 제목3")
                .description("테스트 내용")
                .target("baby")
                .startingPrice(30000)
                .immediatePrice(70000)
                .endDate(LocalDateTime.parse("2024-11-17T13:40:00.000000"))
                .build();
        auctionRepository.saveAndFlush(auction3);

        Category category4 = categoryRepository.findById(9L).get();
        Auction auction4 = Auction.builder()
                .regUser(user)
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

        Category category5 = categoryRepository.findById(1L).get();
        Auction auction5 = Auction.builder()
                .regUser(user)
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