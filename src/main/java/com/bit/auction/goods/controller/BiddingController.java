package com.bit.auction.goods.controller;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.BiddingService;
import com.bit.auction.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/auction")
@RestController
@RequiredArgsConstructor
@Log4j2
public class BiddingController {
    private final BiddingService biddingService;
    private final AuctionService auctionService;



    @GetMapping("/bidding/{id}")
    public ModelAndView bidding(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView();

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(id);
        mav.addObject("getGoods" , auctionDTO);
        mav.setViewName("bidding/bidding.html");
        return mav;

    }

    @GetMapping("/bidding/info/{id}")
    public ModelAndView biddinginfo(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView();

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(id);
        mav.addObject("getGoods" , auctionDTO);
        mav.setViewName("bidding/biddinginfo.html");
        return mav;

    }

    @GetMapping("/impbiddinginfo/{id}")
    public ModelAndView biddingInfo(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView();

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(id);
        mav.addObject("getGoods" , auctionDTO);
        mav.setViewName("bidding/impbiddinginfo.html");
        return mav;

    }
//    @PostMapping("/bidding/info/{id}")
//    public void bidding(BiddingDTO biddingDTO , Auction auctionId , User userId){
//        biddingService.setbid(biddingDTO, auctionId.getId() , userId.getUserId());
//    }

        @PostMapping("/impbiddinginfo/{id}")
    public void bidding(BiddingDTO biddingDTO , Long auctionId , String userId){
        Auction auction = Auction.builder()
                .id(auctionId)
                .build();
           User user = User.builder()
                .userId(userId)
                .build();
        biddingService.setbid(biddingDTO, auction , user);
    }

}
