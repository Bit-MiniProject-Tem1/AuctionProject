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

import java.util.List;

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
    public ModelAndView getGoodsList(@RequestParam(value = "category", required = false) Long categoryId,
                                     @RequestParam(value = "subCategory", required = false) Long subCategoryId,
                                     @RequestParam(value = "etc", required = false) String etcOption,
                                     @PageableDefault(page = 0, size = 12) Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        List<CategoryDTO> categoryList = categoryService.getTopCategoryList();
        mav.addObject("topCategoryList", categoryList);
        if (categoryId == null) {
            mav.addObject("categoryList", categoryList);
        } else {
            categoryList = categoryService.searchSubCategoryList(categoryId);
            mav.addObject("categoryList", categoryList);
        }

        if (categoryId == null && subCategoryId == null) {
            mav.addObject("auctionList", auctionService.getAuctionList(pageable, null, "all"));
            mav.addObject("topCategoryName", "전체");
        } else {
            mav.addObject("topCategoryName", categoryService.getCategoryName(categoryId));
            if (subCategoryId != null) {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, subCategoryId, "sub"));
            } else if (etcOption != null) {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, categoryId, "etc"));
            } else {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, categoryId, "top"));
            }
        }

        mav.setViewName("auction/getAuctionList.html");

        return mav;
    }

    @GetMapping("/my")
    public ModelAndView getMyAuction() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("mypage/getMyAuctionList.html");

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
