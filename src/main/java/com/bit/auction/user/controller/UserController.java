package com.bit.auction.user.controller;

import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.user.dto.ResponseDTO;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuctionService auctionService;

    @GetMapping("/join")
    public ModelAndView getJoin() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/join.html");

        return mav;
    }

    @GetMapping("/find_id")
    public ModelAndView getFindId() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/find_id.html");

        return mav;
    }

    @GetMapping("/find_id2")
    public ModelAndView getFindId2() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/find_id2.html");

        return mav;
    }

    @GetMapping("/find_password")
    public ModelAndView getFindPassword() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/find_password.html");

        return mav;
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/login.html");

        return mav;
    }

    @GetMapping("/mypage")
    public ModelAndView getMyPage() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/mypage.html");

        return mav;
    }

    @GetMapping("/profile")
    public ModelAndView getMyProfile() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/profile.html");

        return mav;
    }

    @GetMapping("/myInquiry")
    public ModelAndView getMyInquiry() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/myinquiry.html");

        return mav;
    }

    @GetMapping("/inquiry")
    public ModelAndView getInquiry() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/inquiry.html");

        return mav;
    }

    @GetMapping("/reg-goods")
    public ModelAndView getMyAuction(@RequestParam(required = false) String status,
                                     @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ModelAndView mav = new ModelAndView();

        String regUserId = "kim";

        mav.addObject("auctionList", auctionService.getMyAuctionList(pageable, regUserId, status));
        mav.setViewName("user/mypage/getMyAuctionList.html");

        return mav;
    }
    // --------------------------------------------------------- //

    @PostMapping("/id-check")
    public ResponseEntity<?> idCheck(UserDTO userDTO) {
        System.out.println(userDTO.getUserId());
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        Map<String, String> returnMap = new HashMap<>();

        try {
            int idCheck = userService.idCheck(userDTO.getUserId());

            if (idCheck == 0) {
                returnMap.put("idCheckMsg", "idOk");
            } else {
                returnMap.put("idCheckMsg", "idFail");
            }

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorCode(501);
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }


}
