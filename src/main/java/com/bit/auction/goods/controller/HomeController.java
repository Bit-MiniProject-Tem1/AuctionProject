package com.bit.auction.goods.controller;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.service.AuctionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.bit.auction.goods.service.AuctionService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

public class HomeController {
    private final AuctionService auctionService;

    @GetMapping("/")
    public String home(Model model) {

        List<AuctionDTO> recentAuctions = auctionService.findByForRecentList();
        model.addAttribute("recentAuctions", recentAuctions);

        List<AuctionDTO> fianlAuctions = auctionService.findByForFinalList();
        model.addAttribute("fianlAuctions", fianlAuctions);

        model.addAttribute("topCategoryName", "전체");



        return "index";
    }

}
