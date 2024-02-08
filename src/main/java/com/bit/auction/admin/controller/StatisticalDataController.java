package com.bit.auction.admin.controller;

import com.bit.auction.admin.service.StatisticalDataService;
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

    @GetMapping("/admin/statisticalData")
    public ModelAndView statisticalMainView() {
        ModelAndView mv = new ModelAndView();

        List<String> monthList = dataService.getStatisticalPeriod();
        List<Integer> biddingCountList = dataService.getBiddingCountList();
        List<Integer> auctionCountList = dataService.getAuctionCountList();
        List<Integer> totalPriceList = dataService.getTotalPriceList();

        int biddingCountSum = 0;
        int auctionCountSum = 0;
        int totalPriceSum = 0;

        for(int i = 0; i < monthList.size(); i++) {
            biddingCountSum = biddingCountSum + biddingCountList.get(i);
            auctionCountSum = auctionCountSum + auctionCountList.get(i);
            totalPriceSum = totalPriceSum + totalPriceList.get(i);
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
