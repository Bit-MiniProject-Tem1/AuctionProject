package com.bit.auction.user.controller;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.entity.Point;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.PointService;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    @Autowired
    private UserService userService;
    private PointService pointService;

    private final AuctionService auctionService;

    @GetMapping("/mypage-view")
    public ModelAndView getMyPage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();

        UserDTO loginUser = customUserDetails.getUser().toDTO();

        if (loginUser == null) {
            mav.setViewName("user/login/login");
        } else {
            mav.setViewName("user/mypage/mypage.html");

        }
        int[] index = {0};
        List<AuctionDTO> likeList = auctionService.findByUserId(customUserDetails.getUser().getId()).stream().filter(auctionDTO -> index[0]++ < 4).toList();
        mav.addObject("likeList", likeList);
        List<Map<String, Long>> likeSumList = auctionService.getLikeSumList();

        List<Map<String, Long>> userLikeList;

        if(customUserDetails != null) {
            userLikeList = auctionService.getUserLikeList(customUserDetails.getUser().getId());
            mav.setViewName("user/mypage/mypage.html");
        } else {
            mav.setViewName("user/login/login");
            userLikeList = new ArrayList<>();
        }

        if(!userLikeList.isEmpty()) {
            likeList.stream().map(auctionDTO -> {
                userLikeList.forEach(map -> {
                    if(map.get("AUCTION_ID") == auctionDTO.getId()) {
                        auctionDTO.setLikeChk(true);
                    }
                });
                return auctionDTO;
            }).collect(Collectors.toList());
        }

        likeList.forEach(auctionDTO -> {
            auctionDTO.setLikeCnt(
                    likeSumList.stream().filter(stringLongMap -> auctionDTO.getId() == stringLongMap.get("AUCTION_ID"))
                            .flatMap(map -> map.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                            .get("LIKE_SUM"));
        });
        return mav;

    }

    @GetMapping("/mylikeproduct")
    public ModelAndView getMyAuctionLikeProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView mav = new ModelAndView();

        List<AuctionDTO> likeList = auctionService.findByUserId(customUserDetails.getUser().getId());
        mav.addObject("likeList", likeList);
        List<Map<String, Long>> likeSumList = auctionService.getLikeSumList();

        List<Map<String, Long>> userLikeList;

        if(customUserDetails != null) {
            userLikeList = auctionService.getUserLikeList(customUserDetails.getUser().getId());
            mav.setViewName("user/mypage/mypage.html");
        } else {
            mav.setViewName("user/login/login");
            userLikeList = new ArrayList<>();
        }

        if(!userLikeList.isEmpty()) {
            likeList.stream().map(auctionDTO -> {
                userLikeList.forEach(map -> {
                    if(map.get("AUCTION_ID") == auctionDTO.getId()) {
                        auctionDTO.setLikeChk(true);
                    }
                });
                return auctionDTO;
            }).collect(Collectors.toList());
        }

        likeList.forEach(auctionDTO -> {
            auctionDTO.setLikeCnt(
                    likeSumList.stream().filter(stringLongMap -> auctionDTO.getId() == stringLongMap.get("AUCTION_ID"))
                            .flatMap(map -> map.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                            .get("LIKE_SUM"));
        });
        mav.setViewName("user/mypage/getMyLikeProductList.html");

        return mav;
    }

@GetMapping("/point")
public ModelAndView getPointPage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();

        UserDTO loginUser = customUserDetails.getUser().toDTO();

        String userId = customUserDetails.getUsername();
        PointDTO pointDTO = pointService.getPoint(userId);
        mav.addObject("getPoint", pointDTO);
        if (loginUser == null) {
            mav.setViewName("user/login/login");
        } else {
            mav.setViewName("user/mypage/mypoint.html");
        }
        return mav;
}

}
