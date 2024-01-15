package com.bit.auction.user.controller;

import com.bit.auction.goods.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final AuctionService auctionService;

    @GetMapping("/reg-goods")
    public ModelAndView getMyAuction() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/getMyAuctionList.html");

        return mav;
    }

}
