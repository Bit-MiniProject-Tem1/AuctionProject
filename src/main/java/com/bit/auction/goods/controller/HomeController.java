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


import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

        /**
         * [
         *      {
         *          "AUCTION_ID": 1,
         *          "LIKE_SUM": 1
         *      },
         *      {
         *          "AUCTION_ID": 2,
         *          "LIKE_SUM": 2
         *      },
         *      .....
         *
         * ]
         */

        List<Map<String, Long>> likeSumList = auctionService.getLikeSumList();

        recentAuctions.forEach(auctionDTO -> {
            auctionDTO.setLikeCnt(
                    likeSumList.stream().filter(stringLongMap -> auctionDTO.getId() == stringLongMap.get("AUCTION_ID"))
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    .get("LIKE_SUM"));
        });

        model.addAttribute("recentAuctions", recentAuctions);

        List<AuctionDTO> finalAuctions = auctionService.findByForFinalList();
        model.addAttribute("finalAuctions", finalAuctions);

        return "index";
    }
}
