package com.bit.auction.goods.controller;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.BiddingService;
import com.bit.auction.goods.service.PointService;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/auction")
@RestController
@RequiredArgsConstructor
@Log4j2
public class BiddingController {
    private final BiddingService biddingService;
    private final AuctionService auctionService;
    private final UserService userService;
    private final PointService pointService;



    @GetMapping("/bidding/{id}")
    public ModelAndView bidding(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView();

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(id);
        mav.addObject("getGoods" , auctionDTO);
        mav.setViewName("bidding/bidding.html");
        return mav;

    }

    @GetMapping("/bidding/info/{id}")
    public ModelAndView biddinginfo(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView();

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(id);
        mav.addObject("getGoods" , auctionDTO);
        mav.setViewName("bidding/biddinginfo.html");
        return mav;

    }

    @GetMapping("/impbiddinginfo/{id}")
    public ModelAndView biddingInfo(@PathVariable("id") Long id,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(id);

        String userId = customUserDetails.getUsername();

        UserDTO userDTO = userService.findUser(userId);

        PointDTO pointDTO = pointService.getPoint(userId);

        mav.addObject("getGoods" , auctionDTO);
        mav.addObject("getUser" , userDTO);
        mav.addObject("getPoint" , pointDTO);
        mav.setViewName("bidding/impbiddinginfo.html");
        return mav;

    }

    @PostMapping("/impbiddinginfo/{id}")
    public void bidding(@RequestBody BiddingDTO biddingDTO,
                        @AuthenticationPrincipal CustomUserDetails customUserDetails){
        biddingService.updateBidStatus();
        biddingService.setbid(biddingDTO);
        pointService.pointWithdraw(biddingDTO.getBiddingPrice() , customUserDetails.getUsername());

    }




}
