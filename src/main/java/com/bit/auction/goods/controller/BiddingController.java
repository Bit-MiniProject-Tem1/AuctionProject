package com.bit.auction.goods.controller;

import com.bit.auction.goods.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/bidinfo")
    public ModelAndView biddinginfo() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("bidding/biddinginfo.html");
        return mav;

    }
//    @PostMapping("/bidding")
//    public void biddingPrice(@RequestBody final BiddingRequestDTO biddingRequestDTO){
//       biddingService.biddingPrice(biddingRequestDTO);
//    }

}
