package com.bit.auction.admin.controller;

import com.bit.auction.admin.service.StatisticalDataService;
import com.bit.auction.goods.entity.Bidding;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StatisticalDataController {

    private final StatisticalDataService dataService;

    @GetMapping("/statisticalData")
    public ModelAndView statisticalMainView() {
        ModelAndView mv = new ModelAndView();

        String[] monthList = dataService.getStatisticalPeriod();
        int[] biddingCountList = dataService.getBiddingCountList();
        int[] auctionCountList = dataService.getAuctionCountList();
        int[] totalPriceList = dataService.getTotalPriceList();

        mv.addObject("monthList", monthList);
        mv.addObject("biddingCountList", biddingCountList);
        mv.addObject("auctionCountList", auctionCountList);
        mv.addObject("totalPriceList", totalPriceList);
        mv.setViewName("/admin/statisticalData.html");
        return mv;
    }



}
