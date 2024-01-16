package com.bit.auction.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FAQ_Controller {

    @GetMapping("/faq_main")
    public String faqMain() {
        return "/user/customer/faq_main";
    }

    @GetMapping("/faq_detail")
    public String faqDetail() {
        return "/user/customer/faq_detail";
    }
}
