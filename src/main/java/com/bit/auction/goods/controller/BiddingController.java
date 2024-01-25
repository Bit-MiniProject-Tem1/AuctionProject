package com.bit.auction.goods.controller;

import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.service.BiddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/auction")
@RestController
@RequiredArgsConstructor
@Log4j2
public class BiddingController {
    private final BiddingService biddingService;

    @GetMapping("/bid")
    public ModelAndView bidding() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("bidding/bidding.html");
        return mav;

    }

    @GetMapping("/bidinfo")
    public ModelAndView biddinginfo() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("bidding/biddinginfo.html");
        return mav;

    }

    @GetMapping(value = "/bidding/{auctionId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BiddingDTO>> getListByAuction(@PathVariable("auctionId") Long auctionId){

        log.info("auctionId" + auctionId);
        return new ResponseEntity<>( biddingService.getBidList(auctionId), HttpStatus.OK);
    }

    @GetMapping(value = "/mypage/bidding/{userId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BiddingDTO>> getListByUser(@PathVariable("userId") Long userId){

        log.info("userId" + userId);
        return new ResponseEntity<>( biddingService.getUserBidList(userId), HttpStatus.OK);
    }

//    @PostMapping("/bidinfo")
//    public void bidding(BiddingDTO biddingDTO ,Long auctionId , Long userId ){
//        biddingService.insertBid(biddingDTO,auctionId , userId);
//    }

}
