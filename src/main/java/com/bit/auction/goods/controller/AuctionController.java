package com.bit.auction.goods.controller;

import com.bit.auction.common.CkEditorImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final CkEditorImageUtils ckEditorImageUtils;

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
    public ModelAndView getGoodsList() {
        ModelAndView mav = new ModelAndView();

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
