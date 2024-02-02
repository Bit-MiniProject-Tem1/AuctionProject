package com.bit.auction.admin.controller;

import com.bit.auction.common.FileUtils;
import com.bit.auction.user.dto.FaqAttachedFileDTO;
import com.bit.auction.user.dto.FaqDTO;
import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.service.FaqService;
import com.bit.auction.user.service.InquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class InquiryManagementController {
    private final FaqService faqService;
    private final InquiryService inquiryService;
    private final FileUtils fileUtils;

    @Value("${ncp.bucket}")
    private String bucketName;

    @Value("${ncp.endPoint}")
    private String storageUrl;

/*    @GetMapping("/inquiryManagement")
    public String inquiryManagement() {
        return "/admin/inquiryManagement";
    }*/

    @GetMapping(value = {"/inquiry-management_main", "/inquiry-management_search"})
    public ModelAndView inquiryManagementMain(@PageableDefault(page = 0, size = 20) Pageable pageable, FaqDTO faqDTO, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();
        String category = faqDTO.getCategory();
        String condition = faqDTO.getSearchCondition();
        String keyword = faqDTO.getSearchKeyword();

        mv.addObject("faqList", faqService.getFaqList(pageable, faqDTO));
        mv.addObject("category", category == null ? "전체" : category);
        mv.addObject("searchCondition", condition == null ? "전체" : condition);
        mv.addObject("searchKeyword", keyword == null ? "" : keyword);
        mv.addObject("requestUrl", request.getRequestURL().toString());

        log.info("##### requestUrl={}", request.getRequestURL().toString());

        mv.setViewName("/admin/inquiryManagement.html");

        return mv;
    }


    @GetMapping("/faq_add")
    public String faqAddView() {
        return "/admin/faq_add";
    }

    @PostMapping("/faq_add")
    public String faqSave(FaqDTO faqDTO, RedirectAttributes redirectAttributes, MultipartFile[] uploadFiles, HttpServletRequest request, HttpServletResponse response) {

        String fileNames = "";

        try {
            List<FaqAttachedFileDTO> faqAttachedFileDTOList = new ArrayList<>();

            for (MultipartFile file : uploadFiles) {
                if (file.getOriginalFilename() != null &&
                        !file.getOriginalFilename().equals("")) {
                    FaqAttachedFileDTO faqAttachedFileDTO = fileUtils.parseFaqAttachedFileInfo(file, "faq/");

                    faqAttachedFileDTO.setFaqId(faqDTO.getFaqId());

                    faqAttachedFileDTOList.add(faqAttachedFileDTO);

                    fileNames = fileNames + faqAttachedFileDTO.getFileName() + "  ";
                }
            }
            fileNames.trim();
            faqDTO.setFaqAttachedFileDTOList(faqAttachedFileDTOList);
            faqService.insertFaq(faqDTO);
            Map<String, String> returnMap = new HashMap<>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            /*response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());*/
            response.setStatus(HttpStatus.OK.value());
//            redirectAttributes.addAttribute("StatusCode", HttpStatus.OK.value());

//            return ResponseEntity.ok(response);
        } catch (Exception e) {
            if (faqDTO.getCategory().equals("")) {
                redirectAttributes.addAttribute("ErrorCode", 602);
                redirectAttributes.addAttribute("ErrorMessage", "카테고리를 입력하세요.");
            } else if (faqDTO.getTitle().equals("")) {
                redirectAttributes.addAttribute("ErrorCode", 603);
                redirectAttributes.addAttribute("ErrorMessage", "제목을 입력하세요.");
            } else if (faqDTO.getContent().equals("")) {
                redirectAttributes.addAttribute("ErrorCode", 604);
                redirectAttributes.addAttribute("ErrorMessage", "내용을 입력하세요.");
            } else {
                redirectAttributes.addAttribute("ErrorCode", 605);
                redirectAttributes.addAttribute("ErrorMessage", e.getMessage());
            }
//            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            redirectAttributes.addAttribute("StatusCode", HttpStatus.BAD_REQUEST.value());

            faqService.insertFaq(faqDTO);
        }


        redirectAttributes.addFlashAttribute("faqDTO", faqDTO);
        redirectAttributes.addFlashAttribute("fileNames", fileNames);
        redirectAttributes.addAttribute("itemId", faqDTO.getFaqId());
        redirectAttributes.addAttribute("status", true);
        redirectAttributes.addAttribute("requestUrl", request.getRequestURL().toString());

        log.info("############ requestUrl : " + request.getRequestURL().toString());

        return "redirect:/inquiry-management_main";
    }

/*    @GetMapping("/faq_add_result")
    public String faqAddResult() {
        return "/admin/faq_add_result";
    }*/

    @GetMapping("/faq_modification")
    public String faqModification(FaqDTO faqDTO, RedirectAttributes redirectAttributes, MultipartFile[] uploadFiles, HttpServletRequest request, HttpServletResponse response) {
        redirectAttributes.addFlashAttribute("faqDTO", faqDTO);
//        redirectAttributes.addFlashAttribute("fileNames", fileNames);
        redirectAttributes.addAttribute("itemId", faqDTO.getFaqId());
        redirectAttributes.addAttribute("status", true);
        redirectAttributes.addAttribute("requestUrl", request.getRequestURL().toString());

        return "redirect:/faq_add";
    }

    @GetMapping("/inquiry_management/faq_detail/faq-{faqId}")
    public ModelAndView getBoard(@PathVariable("faqId") Long faqId, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        /*FaqDTO faqDTO = faqService.findById(faqId);
        faqService.updateViewsCount(faqId);*/

        mv.addObject("faqDTO", faqService.updateViewsCount(faqId));
        mv.addObject("requestUrl", request.getRequestURL().toString());
        mv.setViewName("/user/customer/faq_detail.html");

        return mv;
    }

/*    @PostMapping("/faq_modification/faq-{faqId}")
    public ModelAndView faqModification(@PathVariable("faqId") Long faqId, FaqDTO faqDTO, HttpServletRequest request) {

    }*/


}
