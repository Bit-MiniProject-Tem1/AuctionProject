package com.bit.auction.goods.controller;

import com.bit.auction.common.CkEditorImageUtils;
import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final CkEditorImageUtils ckEditorImageUtils;
    private final AuctionService auctionService;
    private final CategoryService categoryService;

    @GetMapping("/register")
    public ModelAndView registrationAuction() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("auction/registerAuction.html");

        return mav;
    }

    @GetMapping("/goods")
    public ModelAndView getGoods() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("auction/getAuction.html");

        return mav;
    }

    @GetMapping("/goods-list")
    public ModelAndView getGoodsList(@RequestParam(required = false) Map<String, Object> paramMap,
                                     @PageableDefault(page = 0, size = 12) Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        List<CategoryDTO> categoryList = categoryService.getTopCategoryList();
        mav.addObject("topCategoryList", categoryList);
        if (paramMap.get("category") == null) {
            mav.addObject("categoryList", categoryList);
        } else {
            categoryList = categoryService.searchSubCategoryList(Long.valueOf(String.valueOf(paramMap.get("category"))));
            mav.addObject("categoryList", categoryList);
        }

        List<String> targetList = new ArrayList<>();
        if (paramMap.get("target") != null) {
            targetList = Arrays.asList(paramMap.get("target").toString().split(","));
        }

        if (paramMap.get("category") == null && paramMap.get("subCategory") == null) {
            mav.addObject("auctionList", auctionService.getAuctionList(pageable, null, "all", targetList));
            mav.addObject("topCategoryName", "전체");
        } else {
            Long categoryId = Long.valueOf(String.valueOf(paramMap.get("category")));
            mav.addObject("topCategoryName", categoryService.getCategoryName(categoryId));

            if (paramMap.get("subCategory") != null) {
                Long subCategoryId = Long.valueOf(String.valueOf(paramMap.get("subCategory")));
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, subCategoryId, "sub", targetList));
            } else if (paramMap.get("etc") != null) {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, categoryId, "etc", targetList));
            } else {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, categoryId, "top", targetList));
            }
        }

        mav.setViewName("auction/getAuctionList.html");

        return mav;
    }
    
    @PostMapping(value = "/img/upload")
    public ModelAndView image(MultipartHttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        MultipartFile uploadFile = request.getFile("upload");
        String realPath = request.getServletContext().getRealPath("/upload");
        log.info(">>> " + realPath);
        String uploadPath = ckEditorImageUtils.parseFileInto(uploadFile, realPath);
        mav.addObject("uploaded", true);
        mav.addObject("url", uploadPath);

        return mav;
    }
}
