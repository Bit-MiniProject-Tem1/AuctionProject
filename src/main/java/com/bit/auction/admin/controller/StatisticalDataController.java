package com.bit.auction.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StatisticalDataController {
    @GetMapping("/statistical_main")
    public String statisticalMainView() {
        return "/admin/statisticalData";
    }



}
