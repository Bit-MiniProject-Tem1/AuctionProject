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

import java.util.ArrayList;
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
        List<AuctionDTO> finalAuctions = auctionService.findByForFinalList();
        List<AuctionDTO> popularAuctions = auctionService.findByForPopularList();
        List<Map<String, Long>> likeSumList = auctionService.getLikeSumList();

        List<Map<String, Long>> userLikeList;

        if(customUserDetails != null) {
            userLikeList = auctionService.getUserLikeList(customUserDetails.getUser().getId());
            List<AuctionDTO> likeList = auctionService.findByUserId(customUserDetails.getUser().getId());
            model.addAttribute("likeList", likeList);
        } else {
            userLikeList = new ArrayList<>();
        }

        if(!userLikeList.isEmpty()) {
            recentAuctions.stream().map(auctionDTO -> {
                userLikeList.forEach(map -> {
                    if(map.get("AUCTION_ID") == auctionDTO.getId()) {
                        auctionDTO.setLikeChk(true);
                    }
                });
                return auctionDTO;
            }).collect(Collectors.toList());
        }

        recentAuctions.forEach(auctionDTO -> {
            auctionDTO.setLikeCnt(
                    likeSumList.stream().filter(stringLongMap -> auctionDTO.getId() == stringLongMap.get("AUCTION_ID"))
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    .get("LIKE_SUM"));
        });

        if(!userLikeList.isEmpty()) {
            finalAuctions.stream().map(auctionDTO -> {
                userLikeList.forEach(map -> {
                    if(map.get("AUCTION_ID") == auctionDTO.getId()) {
                        auctionDTO.setLikeChk(true);
                    }
                });
                return auctionDTO;
            }).collect(Collectors.toList());
        }

        finalAuctions.forEach(auctionDTO -> {
            auctionDTO.setLikeCnt(
                    likeSumList.stream().filter(stringLongMap -> auctionDTO.getId() == stringLongMap.get("AUCTION_ID"))
                            .flatMap(map -> map.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                            .get("LIKE_SUM"));
        });

        if(!userLikeList.isEmpty()) {
            popularAuctions.stream().map(auctionDTO -> {
                userLikeList.forEach(map -> {
                    if(map.get("AUCTION_ID") == auctionDTO.getId()) {
                        auctionDTO.setLikeChk(true);
                    }
                });
                return auctionDTO;
            }).collect(Collectors.toList());
        }

        popularAuctions.forEach(auctionDTO -> {
            auctionDTO.setLikeCnt(
                    likeSumList.stream().filter(stringLongMap -> auctionDTO.getId() == stringLongMap.get("AUCTION_ID"))
                            .flatMap(map -> map.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                            .get("LIKE_SUM"));
        });

        model.addAttribute("customUserDetails", customUserDetails);

        model.addAttribute("popularAuctions", popularAuctions);

        model.addAttribute("recentAuctions", recentAuctions);

        model.addAttribute("finalAuctions", finalAuctions);

        return "index";
    }

}
