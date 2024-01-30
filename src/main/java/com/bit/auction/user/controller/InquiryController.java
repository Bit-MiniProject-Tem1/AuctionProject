package com.bit.auction.user.controller;

import com.bit.auction.common.FileUtils;
import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.InquiryFileDTO;
import com.bit.auction.user.dto.ResponseDTO;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.entity.Inquiry;
import com.bit.auction.user.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private FileUtils fileUtils;

    private final Logger logger = LoggerFactory.getLogger(InquiryController.class);

    @GetMapping("/inquiry-view")
    public ModelAndView getInquiry() {

        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/insertInquiry.html");

        return mav;
    }

    @GetMapping("/inquiry/{inquiryNo}")
    public ModelAndView getInquiry(@PathVariable("inquiryNo") Long inquiryNo) {
        ModelAndView mav = new ModelAndView();

        logger.info("inquiryNo: {}", inquiryNo);
        logger.info("getInquiry: {}", inquiryService.getInquiry(inquiryNo));

        mav.addObject("getInquiry", inquiryService.getInquiry(inquiryNo));
        mav.setViewName("user/mypage/getInquiry.html");

        return mav;
    }

    @PostMapping("/inquiry")
    public ResponseEntity<?> insertInquiry(InquiryDTO inquiryDTO,
                                        @RequestParam("uploadFiles") MultipartFile[] uploadFiles) {
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
        System.out.println("--------------------------------------------------");
        System.out.println(inquiryDTO);

        try {
            List<InquiryFileDTO> inquiryFileDTOList = new ArrayList<>();

            for (MultipartFile file : uploadFiles) {
                if (file.getOriginalFilename() != null &&
                    !file.getOriginalFilename().equals("")) {
                    InquiryFileDTO inquiryFileDTO = fileUtils.parseFileInfo(file, "inquiry/");

                    inquiryFileDTOList.add(inquiryFileDTO);
                }
            }
            inquiryDTO.setInquiryFileDTOList(inquiryFileDTOList);
            inquiryService.insertInquiry(inquiryDTO);
            Map<String, String> returnMap = new HashMap<>();

            returnMap.put("msg", "정상적으로 입력되었습니다.");

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            if (inquiryDTO.getInquiryTitle().equals("")) {
                response.setErrorCode(602);
                response.setErrorMessage("제목을 입력하세요.");
            } else if (inquiryDTO.getInquiryContent().equals("")) {
                response.setErrorCode(603);
                response.setErrorMessage("내용을 입력하세요.");
            } else {
                response.setErrorCode(605);
                response.setErrorMessage(e.getMessage());
            }

            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }


    }

    @GetMapping("/myInquiry-view")
    public ModelAndView getMyInquiry(Model model) {
        List<InquiryDTO> inquiryDTOList = inquiryService.findAll();

        model.addAttribute("inquiryList", inquiryDTOList);

        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/myinquiry.html");

        return mav;
    }




}
