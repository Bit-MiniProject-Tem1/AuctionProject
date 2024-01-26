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
import com.bit.auction.goods.service.AuctionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

public class HomeController {
    private final AuctionService auctionService;
    private final LikeCntService likeCntService;
    @GetMapping("/")
    public String home(AuctionDTO auctionDTO, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        List<AuctionDTO> recentAuctions = auctionService.findByForRecentList();
        model.addAttribute("recentAuctions", recentAuctions);

        List<AuctionDTO> fianlAuctions = auctionService.findByForFinalList();
        model.addAttribute("fianlAuctions", fianlAuctions);

        if(customUserDetails != null) {
            long likeCnt = likeCntService.findByUserIdAndAuctionId(customUserDetails.getUser().getId(), auctionDTO.getId());
            model.addAttribute("likeCnt", likeCnt);
        }

        long likeSum = likeCntService.findByAuctionId(auctionDTO.getId());
        model.addAttribute("likeSum", likeSum);

        model.addAttribute("topCategoryName", "전체");

        return "index";
    }

}
