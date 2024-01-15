package com.bit.auction.bidding.controller;

import com.bit.auction.bidding.dto.BiddingRequestDTO;
import com.bit.auction.bidding.entity.Bidding;
import com.bit.auction.bidding.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class BiddingController {

    private final BiddingService biddingService;

    @GetMapping("/bid")
    public ModelAndView bidding() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("bidding/bidding.html");
        return mav;

    }
//    @PostMapping("/bidding")
//    public void biddingPrice(@RequestBody final BiddingRequestDTO biddingRequestDTO){
//       biddingService.biddingPrice(biddingRequestDTO);
//    }

}
