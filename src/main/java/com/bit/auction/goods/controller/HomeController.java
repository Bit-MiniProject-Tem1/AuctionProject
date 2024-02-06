package com.bit.auction.goods.controller;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.LikeCntService;
import com.bit.auction.user.entity.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequiredArgsConstructor

public class HomeController {
    private final AuctionService auctionService;
    private final LikeCntService likeCntService;
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        List<AuctionDTO> recentAuctions = auctionService.findByForRecentList();
        List<AuctionDTO> finalAuctions = auctionService.findByForFinalList();
        List<AuctionDTO> popularAuctions = auctionService.findByForPopularList();
       
        auctionService.likeList(customUserDetails, recentAuctions);
        auctionService.likeList(customUserDetails, finalAuctions);
        auctionService.likeList(customUserDetails, popularAuctions);


        model.addAttribute("customUserDetails", customUserDetails);

        model.addAttribute("popularAuctions", popularAuctions);

        model.addAttribute("recentAuctions", recentAuctions);

        model.addAttribute("finalAuctions", finalAuctions);

        return "index";
    }
}
