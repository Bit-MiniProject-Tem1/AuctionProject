package com.bit.auction.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

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

    @GetMapping("/myprofile")
    public ModelAndView getMyProfile() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/myprofile.html");

        return mav;
    }

    @GetMapping("/buying")
    public ModelAndView getBuying() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/buying.html");

        return mav;
    }





}
