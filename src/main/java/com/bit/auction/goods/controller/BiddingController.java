package com.bit.auction.goods.controller;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.BiddingService;
import com.bit.auction.goods.service.PointHistoryService;
import com.bit.auction.goods.service.PointService;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;


@RequestMapping("/auction")
@RestController
@RequiredArgsConstructor
@Log4j2
public class BiddingController {
    private final BiddingService biddingService;
    private final AuctionService auctionService;
    private final UserService userService;
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;



    @GetMapping("/bidding/{id}")
    public ModelAndView bidding(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView();

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(id);
        mav.addObject("getGoods" , auctionDTO);
        mav.setViewName("bidding/bidding.html");
        return mav;

    }


    @GetMapping("/bidding/info/{id}")
    public ModelAndView info(@PathVariable Long id,
                             @RequestParam int biddingPrice,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(id);

        String userId = customUserDetails.getUsername();
        UserDTO userDTO = userService.findUser(userId);

        PointDTO pointDTO = pointService.getPoint(userId);

        mav.addObject("getGoods" , auctionDTO);
        mav.addObject("getUser" , userDTO);
        mav.addObject("getPoint" , pointDTO);
        mav.addObject("Price", biddingPrice);
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
        biddingDTO.setStatus('u');
        biddingDTO.setUserId(customUserDetails.getUsername());
        biddingDTO.setDate(LocalDateTime.now());
        biddingDTO.setPayment(1);
        biddingDTO.setAuctionStatus('E');
        pointService.pointWithdraw(biddingDTO.getBiddingPrice() , customUserDetails.getUsername());
        pointHistoryService.setPointHistory(biddingDTO.getBiddingPrice() , customUserDetails.getUsername() , 'b');

        Long auctionId = biddingDTO.getAuctionId();
        biddingService.updateBidStatus(auctionId);
        biddingService.setbid(biddingDTO);
        int BiddingPrice = biddingDTO.getBiddingPrice();
        auctionService.setCurrentBiddingPrice(auctionId, BiddingPrice);
    }

    @PostMapping("/bidding/info/{id}")
    public void bidding2(@RequestBody BiddingDTO biddingDTO,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        biddingDTO.setStatus('u');
        biddingDTO.setAuctionStatus('S');
        biddingDTO.setUserId(customUserDetails.getUsername());
        biddingDTO.setDate(LocalDateTime.now());
        pointService.pointWithdraw(biddingDTO.getBiddingPrice() , customUserDetails.getUsername());
        pointHistoryService.setPointHistory(biddingDTO.getBiddingPrice() , customUserDetails.getUsername() , 'b');

        Long auctionId = biddingDTO.getAuctionId();
        biddingService.updateBidStatus(auctionId);
        biddingService.setbid(biddingDTO);
        int BiddingPrice = biddingDTO.getBiddingPrice();
        auctionService.setCurrentBiddingPrice(auctionId, BiddingPrice);
    }
}
