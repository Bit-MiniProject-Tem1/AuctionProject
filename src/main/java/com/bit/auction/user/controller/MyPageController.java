package com.bit.auction.user.controller;

import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.entity.Point;
import com.bit.auction.goods.service.PointService;
import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.entity.User;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Slf4j
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class MyPageController {

    @Autowired
    private UserService userService;
    private PointService pointService;

    private final Logger logger = LoggerFactory.getLogger(InquiryController.class);

    private final InquiryService inquiryService;

    @GetMapping("/mypage-view")
    public ModelAndView getMyPage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();

        UserDTO loginUser = customUserDetails.getUser().toDTO();

        if (loginUser == null) {
            mav.setViewName("user/login/login");
        } else {
            mav.setViewName("user/mypage/mypage.html");
        }
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

}
