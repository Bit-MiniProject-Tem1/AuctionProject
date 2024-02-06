package com.bit.auction.admin.controller;

import com.bit.auction.common.FileUtils;
import com.bit.auction.admin.dto.FaqDTO;
import com.bit.auction.admin.service.FaqService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;
    private final FileUtils fileUtils;

    @Value("${ncp.bucket}")
    private String bucketName;

    @Value("${ncp.endPoint}")
    private String storageUrl;

    @GetMapping(value = {"/faq_main", "/faq_search"})
    public ModelAndView faqMain(@PageableDefault(page = 0, size = 20) Pageable pageable, FaqDTO faqDTO, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String category = faqDTO.getCategory();
        String condition = faqDTO.getSearchCondition();
        String keyword = faqDTO.getSearchKeyword();

        log.info(request.getRequestURL().toString());

        mv.addObject("faqList", faqService.getFaqList(pageable, faqDTO));
        mv.addObject("category", category == null ? "전체" : category);
        mv.addObject("searchCondition", condition == null ? "전체" : condition);
        mv.addObject("searchKeyword", keyword == null ? "" : keyword);
        mv.addObject("requestUrl", request.getRequestURL().toString());

        mv.setViewName("/user/customer/faq_main.html");

        return mv;
    }

    @GetMapping("/faq_detail")
    public String faqDetail() {
        return "/user/customer/faq_detail";
    }

    @GetMapping("/faq_detail/faq-{faqId}")
    public ModelAndView getBoard(@PathVariable("faqId") Long faqId, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        mv.addObject("faqDTO", faqService.updateViewsCount(faqId));
        mv.addObject("requestUrl", request.getRequestURL().toString());
        mv.setViewName("/user/customer/faq_detail.html");

        return mv;
    }

    @GetMapping("/file_download")
    public ResponseEntity<Resource>fileDownload(HttpServletRequest request) throws MalformedURLException {
        String replaceText = storageUrl + "/" + bucketName + "/";

        String filePath = UriUtils.encode(request.getParameter("filePath").replace(replaceText, ""), StandardCharsets.UTF_8);

        String fileName = UriUtils.encode(request.getParameter("fileName"), StandardCharsets.UTF_8);

        UrlResource urlResource = new UrlResource(replaceText + filePath);

        String contentDisposition = "attachment; filename=\"" + fileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}


