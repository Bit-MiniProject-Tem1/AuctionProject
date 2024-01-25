package com.bit.auction.Junit.repository;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import com.bit.auction.goods.repository.AuctionRepository;
import com.bit.auction.goods.repository.BiddingRepository;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class BiddingTest {

    @Autowired
    private BiddingRepository biddingRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void savePrice () {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            long id = (long)(Math.random() * 100) + 1;
            long id2 = (long)(Math.random() * 5) + 1;
            User user = User.builder().id(id).build();
            Auction auction = Auction.builder().id(id2).build();
            Bidding bidding = Bidding.builder()
                    .biddingPrice(i += 1)
                    .date(LocalDateTime.now())
                    .payment(1)
                    .auction(auction)
                    .user(user)
                    .build();

                    biddingRepository.save(bidding);

        });
    }
}

