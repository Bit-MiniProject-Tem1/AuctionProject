package com.bit.auction.admin.controller;

import com.bit.auction.admin.service.StatisticalDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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

        int biddingCountSum = 0;
        int auctionCountSum = 0;
        int totalPriceSum = 0;

        for(int i = 0; i < monthList.length; i++) {
            biddingCountSum = biddingCountSum + biddingCountList[i];
            auctionCountSum = auctionCountSum + auctionCountList[i];
            totalPriceSum = totalPriceSum + totalPriceList[i];
        }

        mv.addObject("monthList", monthList);
        mv.addObject("biddingCountList", biddingCountList);
        mv.addObject("auctionCountList", auctionCountList);
        mv.addObject("totalPriceList", totalPriceList);
        mv.addObject("biddingCountSum", biddingCountSum);
        mv.addObject("auctionCountSum", auctionCountSum);
        mv.addObject("totalPriceSum", totalPriceSum);
        mv.setViewName("/admin/statisticalData.html");
        return mv;
    }



}
