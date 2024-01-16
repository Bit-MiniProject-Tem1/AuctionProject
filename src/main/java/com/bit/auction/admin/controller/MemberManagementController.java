package com.bit.auction.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
