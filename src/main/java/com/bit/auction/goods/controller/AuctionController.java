package com.bit.auction.goods.controller;

import ch.qos.logback.core.model.Model;
import com.bit.auction.common.CkEditorImageUtils;
import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @GetMapping("/recentproducts")
    public ModelAndView RentAuctionProducts() {
        ModelAndView mav = new ModelAndView();

        List<AuctionDTO> recentAuctions = auctionService.findByForRecentList();

        mav.addObject("auctionList", recentAuctions);
        mav.addObject("topCategoryName", "전체");

        mav.setViewName("auction/getAuctionforRecent.html");

        return mav;
    }


    @GetMapping("/finalproducts")
    public ModelAndView FinalAuctionProducts() {
        ModelAndView mav = new ModelAndView();

        List<AuctionDTO> fianlAuctions = auctionService.findByForFinalList();

        mav.addObject("auctionList", fianlAuctions);
        mav.addObject("topCategoryName", "전체");

        mav.setViewName("auction/getAuctionforFinal.html");

        return mav;
    }

    @GetMapping("/popularproducts")
    public ModelAndView PopularAuctionProducts() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("auction/getAuctionforPopular.html");

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView registrationAuction() {
        ModelAndView mav = new ModelAndView();

        List<CategoryDTO> categoryList = categoryService.getTopCategoryList();
        mav.addObject("topCategoryList", categoryList);

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
                                     @PageableDefault(page = 0, size = 12) Pageable pageable,
                                     @RequestParam(required = false) String searchQuery) {
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

        List<Character> statusList = new ArrayList<>();
        if (paramMap.get("closing") != null) {
            String[] status = paramMap.get("closing").toString().split(",");
            for (String s : status) {
                statusList.add(s.charAt(0));
            }
        }

        if (paramMap.get("category") == null && paramMap.get("subCategory") == null) {
            mav.addObject("topCategoryName", "전체");

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                List<AuctionDTO> searchResult = auctionService.searchAuctions(searchQuery, statusList);

                // 전체 항목을 가져오기
                Page<AuctionDTO> auctionPage = auctionService.getAuctionList(pageable, null, "all", targetList, statusList);
                List<AuctionDTO> allAuctions = auctionPage.getContent();

                if (!searchResult.isEmpty()) {
                    // 검색 결과가 있으면 전체 항목에 포함된 항목이라면 추가
                    mav.addObject("auctionList", searchResult);
                    mav.addObject("topCategoryName", "검색 결과");

                } else {
                    // 검색 결과가 없으면 전체 항목을 보여주고 메시지 추가
                    mav.addObject("auctionList", allAuctions);
                    mav.addObject("searchMessage", "검색 결과가 없습니다. 전체 항목의 제품을 보여드립니다.");
                    mav.addObject("showAlertValue", true);
                }
            } else {
                // 검색어가 없는 경우에는 전체 목록을 보여줘야 함
                Page<AuctionDTO> auctionPage = auctionService.getAuctionList(pageable, null, "all", targetList, statusList);
                List<AuctionDTO> allAuctions = auctionPage.getContent();
                mav.addObject("auctionList", allAuctions);
            }

        } else {
            Long categoryId = Long.valueOf(String.valueOf(paramMap.get("category")));
            mav.addObject("topCategoryName", categoryService.getCategoryName(categoryId));

            if (paramMap.get("subCategory") != null) {
                Long subCategoryId = Long.valueOf(String.valueOf(paramMap.get("subCategory")));
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, subCategoryId, "sub", targetList, statusList));
            } else if (paramMap.get("etc") != null) {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, categoryId, "etc", targetList, statusList));
            } else {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, categoryId, "top", targetList, statusList));
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
