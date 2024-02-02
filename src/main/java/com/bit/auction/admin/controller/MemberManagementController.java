package com.bit.auction.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class MemberManagementController {

    @GetMapping("/memberManagement")
    public String memberManagement() {
        return "/admin/memberManagement";
    }

    @GetMapping("/member_info")
    public String member_info() {

        return "/admin/member_info";
    }
}
