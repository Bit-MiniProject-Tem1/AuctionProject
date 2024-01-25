package com.bit.auction.Junit.service;

import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.service.BiddingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class biddingServiceTest {

    @Autowired
    private BiddingService biddingService;

    @Test
    public void testbid(){
        Long auctionid = 3L;

        List<BiddingDTO> biddingDTOList = biddingService.getBidList(auctionid);

        biddingDTOList.forEach(biddingDTO -> System.out.println(biddingDTO));

    }
}
