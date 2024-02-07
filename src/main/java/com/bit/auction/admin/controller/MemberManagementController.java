package com.bit.auction.admin.controller;

import com.bit.auction.admin.dto.FaqDTO;
import com.bit.auction.admin.repository.FaqRepository;
import com.bit.auction.admin.service.FaqService;
import com.bit.auction.admin.service.MemberManagementService;
import com.bit.auction.common.FileUtils;
import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.goods.service.CategoryService;
import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

public class MemberManagementController {

    private final MemberManagementService memberManagementService;
    private final CategoryService categoryService;
    private final FileUtils fileUtils;

    @GetMapping(value = {"/admin/memberManagement", "/admin/memberManagement_search"})
    public ModelAndView memberManagementView(@PageableDefault(page = 0, size = 20) Pageable pageable, UserDTO userDTO, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String condition = userDTO.getSearchCondition();
        String keyword = userDTO.getSearchKeyword();
        Page<UserDTO> memberList = memberManagementService.getMemberList(pageable, userDTO);

        mv.addObject("memberList", memberList);
        mv.addObject("searchCondition", condition == null ? "전체" : condition);
        mv.addObject("searchKeyword", keyword == null ? "" : keyword);
        mv.addObject("requestUrl", request.getRequestURL().toString());

        mv.setViewName("/admin/memberManagement.html");

        return mv;
    }

    @GetMapping("/admin/member_info")
    public String member_info_form() {

        return "/admin/member_info";
    }

    @GetMapping("/admin/member_info/member-{id}")
    public ModelAndView getMemberInfo(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        UserDTO member = memberManagementService.findById(id);
        List<InquiryDTO> inquiryList = memberManagementService.findByInquiryWriter(member.getUserId());
        List<BiddingDTO> biddingList = memberManagementService.findByUserId(member.getUserId());

        mv.addObject("member", member);
        mv.addObject("inquiryList", inquiryList);
        mv.addObject("biddingList", biddingList);
        mv.addObject("requestUrl", request.getRequestURL().toString());
        mv.setViewName("/admin/member_info.html");

        return mv;
    }
}
