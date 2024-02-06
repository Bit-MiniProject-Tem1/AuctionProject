package com.bit.auction.user.controller;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.entity.PointHistory;
import com.bit.auction.goods.repository.BiddingRepository;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.BiddingService;
import com.bit.auction.goods.service.PointHistoryService;
import com.bit.auction.goods.service.PointService;
import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.service.InquiryService;
import com.bit.auction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private final UserService userService;
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;
    private final BiddingService biddingService;
    private final AuctionService auctionService;
    private final BiddingRepository biddingRepository;

    private final Logger logger = LoggerFactory.getLogger(InquiryController.class);

    private final InquiryService inquiryService;

    @GetMapping("/mypage-view")
    public ModelAndView getMyPage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();
        String userId = customUserDetails.getUsername();
        mav.addObject("biddingList", biddingService.getMyBiddingList(userId));
        mav.addObject("biddingInfo", biddingService.getbidone(userId));
        mav.addObject("sessionId", userId);
        UserDTO loginUser = customUserDetails.getUser().toDTO();
        mav.addObject("AuctionStatuS", biddingRepository.countAuctionStatusS(customUserDetails.getUsername()));
        mav.addObject("AuctionStatuE", biddingRepository.countAuctionStatusE(customUserDetails.getUsername()));
        mav.addObject("AuctionStatuC", biddingRepository.countAuctionStatusC(customUserDetails.getUsername()));

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

        if (customUserDetails != null) {
            userLikeList = auctionService.getUserLikeList(customUserDetails.getUser().getId());
            mav.setViewName("user/mypage/mypage.html");
        } else {
            mav.setViewName("user/login/login");
            userLikeList = new ArrayList<>();
        }

        if (!userLikeList.isEmpty()) {
            likeList.stream().map(auctionDTO -> {
                userLikeList.forEach(map -> {
                    if (map.get("AUCTION_ID") == auctionDTO.getId()) {
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

        if (customUserDetails != null) {
            userLikeList = auctionService.getUserLikeList(customUserDetails.getUser().getId());
            mav.setViewName("user/mypage/mypage.html");
        } else {
            mav.setViewName("user/login/login");
            userLikeList = new ArrayList<>();
        }

        if (!userLikeList.isEmpty()) {
            likeList.stream().map(auctionDTO -> {
                userLikeList.forEach(map -> {
                    if (map.get("AUCTION_ID") == auctionDTO.getId()) {
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

        List<PointHistory> pointHistoryList = pointHistoryService.getPointHistory(userId);
        mav.addObject("getPoint", pointDTO);

        mav.addObject("pointHistoryList", pointHistoryList);

        if (loginUser == null) {
            mav.setViewName("user/login/login");
        } else {
            mav.setViewName("user/mypage/mypoint.html");
        }
        return mav;
    }

    @GetMapping("/inquiry")
    public ModelAndView getMyInquiryList(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                         InquiryDTO inquiryDTO) {

        ModelAndView mav = new ModelAndView();

        logger.info("searchCondition: {}", inquiryDTO.getSearchCondition());
        logger.info("searchKeyword: {}", inquiryDTO.getSearchKeyword());
        logger.info("inquiryList: {}", inquiryService.getInquiryList(pageable, inquiryDTO));

        mav.addObject("inquiryList", inquiryService.getInquiryList(pageable, inquiryDTO));
        mav.addObject("searchCondition", inquiryDTO.getSearchCondition() == null ? "all" : inquiryDTO.getSearchCondition());
        mav.addObject("searchKeyword", inquiryDTO.getSearchKeyword() == null ? "" : inquiryDTO.getSearchKeyword());

        mav.setViewName("user/mypage/getMyInquiryList.html");

        return mav;

    }


@GetMapping("/biddingList")
public ModelAndView getMyBidding(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
    ModelAndView mav = new ModelAndView();

    String userId = customUserDetails.getUsername();
    mav.addObject("biddingList", biddingService.getMyBiddingList(userId));
    mav.addObject("biddingInfo", biddingService.getbidone(userId));
    mav.addObject("sessionId", userId);

    mav.setViewName("user/mypage/getMyBiddingList.html");

    return mav;
}

    @GetMapping("/chargeForm")
    public ModelAndView chargeFrom(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/chargeForm.html");
        return mav;
    }

    @PostMapping("/chargeForm")
    public void chargePoint(@RequestParam int point,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String userId = customUserDetails.getUsername();
        pointService.pointCharge(point, userId);
        char status = 'c';
        pointHistoryService.setPointHistory(point, userId, status);
    }

    @GetMapping("/withdrawForm")
    public ModelAndView withdrawFrom(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView mav = new ModelAndView();
        PointDTO pointDTO = pointService.getPoint(customUserDetails.getUsername());
        mav.addObject("getPoint", pointDTO);
        mav.setViewName("user/mypage/withdrawForm.html");
        return mav;
    }

    @PostMapping("/withdrawForm")
    public void withdrawPoint(@RequestParam int point,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String userId = customUserDetails.getUsername();
        pointService.pointWithdraw(point, userId);
        char status = 'w';
        pointHistoryService.setPointHistory(point, userId, status);
    }

    @GetMapping("/reg-goods")
    public ModelAndView getMyAuction(@RequestParam(required = false) String status,
                                     @PageableDefault(page = 0, size = 10) Pageable pageable,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView mav = new ModelAndView();

        String regUserId = customUserDetails.getUsername();

        List<Character> statusList = new ArrayList<>();

        if (status == null || status.isEmpty()) {
            statusList.add('S');
            statusList.add('C');
            statusList.add('E');
        } else if (status.equals("S")) {
            statusList.add('S');
        } else if (status.equals("E")) {
            statusList.add('E');
        } else if (status.equals("C")) {
            statusList.add('C');
        }

        mav.addObject("auctionList", auctionService.getMyAuctionList(pageable, regUserId, statusList));
        mav.setViewName("user/mypage/getMyAuctionList.html");

        return mav;
    }

    @GetMapping("/biddingListstatusS")
    public ModelAndView statusS(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
    ModelAndView mav = new ModelAndView();

    String userId = customUserDetails.getUsername();
    mav.addObject("biddingList", biddingService.getMyBiddingList(userId));
    mav.addObject("biddingInfo", biddingService.getbidone(userId));
    mav.addObject("sessionId", userId);

    mav.setViewName("user/mypage/S.html");

    return mav;
}
    @GetMapping("/biddingListstatusE")
    public ModelAndView statusE(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
    ModelAndView mav = new ModelAndView();

    String userId = customUserDetails.getUsername();
    mav.addObject("biddingList", biddingService.getMyBiddingList(userId));
    mav.addObject("biddingInfo", biddingService.getbidone(userId));
    mav.addObject("sessionId", userId);

    mav.setViewName("user/mypage/E.html");

    return mav;
}
    @GetMapping("/biddingListstatusC")
    public ModelAndView statusC(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
    ModelAndView mav = new ModelAndView();

    String userId = customUserDetails.getUsername();
    mav.addObject("biddingList", biddingService.getMyBiddingList(userId));
    mav.addObject("biddingInfo", biddingService.getbidone(userId));
    mav.addObject("sessionId", userId);

    mav.setViewName("user/mypage/C.html");

    return mav;
}
}
