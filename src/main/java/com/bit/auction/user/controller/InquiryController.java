package com.bit.auction.user.controller;

import com.bit.auction.common.FileUtils;
import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.InquiryFileDTO;
import com.bit.auction.user.dto.ResponseDTO;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.entity.Inquiry;
import com.bit.auction.user.service.InquiryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequestMapping("/inquiry")
@RequiredArgsConstructor
@RestController
public class InquiryController {
    private final InquiryService inquiryService;

    private final FileUtils fileUtils;

    private final Logger logger = LoggerFactory.getLogger(InquiryController.class);

    @GetMapping("/inquiry-list")
    public ModelAndView getInquiryList(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                       InquiryDTO inquiryDTO) {
        ModelAndView mav = new ModelAndView();

        logger.info("searchCondition: {}", inquiryDTO.getSearchCondition());
        logger.info("searchKeyword: {}", inquiryDTO.getSearchKeyword());
        logger.info("inquiryList: {}", inquiryService.getInquiryList(pageable, inquiryDTO));

        mav.addObject("inquiryList", inquiryService.getInquiryList(pageable, inquiryDTO));
        mav.addObject("searchCondition", inquiryDTO.getSearchCondition() == null ? "all" : inquiryDTO.getSearchCondition());
        mav.addObject("searchKeyword", inquiryDTO.getSearchKeyword() == null ? "" : inquiryDTO.getSearchKeyword());

        mav.setViewName("user/mypage/getInquiryList.html");

        return mav;
    }

    @GetMapping("/inquiry/{inquiryNo}")
    public ModelAndView getInquiry(@PathVariable("inquiryNo") Long inquiryNo) {
        ModelAndView mav = new ModelAndView();


        logger.info("inquiryNo: {}", inquiryNo);
        logger.info("getInquiry: {}", inquiryService.getInquiry(inquiryNo));

        InquiryDTO inquiryDTO = inquiryService.getInquiry(inquiryNo);

        mav.addObject("getInquiry", inquiryDTO);

//        mav.addObject("getInquiry", inquiryService.getInquiry(inquiryNo));
        mav.setViewName("user/mypage/getInquiry.html");
//        mav.setViewName("user/mypage/testInquiry.html");


        return mav;
    }

    @GetMapping("/insert-inquiry-view")
    public ModelAndView insertInquiryView(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ModelAndView mav = new ModelAndView();

        UserDTO loginUser = customUserDetails.getUser().toDTO();

        if (loginUser == null) {
            mav.setViewName("user/login/login.html");
        } else {
            mav.setViewName("user/mypage/insertInquiry.html");
        }

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

    @PutMapping("/inquiry")
    public ResponseEntity<?> updateInquiry(InquiryDTO inquiryDTO,
                                           MultipartFile[] uploadFiles,
                                           MultipartFile[] changeFiles,
                                           @RequestParam("originFiles") String originFiles) {
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        System.out.println(originFiles);

        try {
            List<InquiryFileDTO> originFileList = new ObjectMapper().readValue(originFiles,
                    new TypeReference<List<InquiryFileDTO>>() {
                    });

            List<InquiryFileDTO> uFileList = new ArrayList<>();
            System.out.println(originFileList);
            for (int i = 0; i < originFileList.size(); i++) {
                if (originFileList.get(i).getInquiryFileStatus().equals("U")) {
                    for (int j = 0; j < changeFiles.length; j++) {
                        if (originFileList.get(i).getNewFileName().equals(
                                changeFiles[j].getOriginalFilename())) {
                            InquiryFileDTO updateInquiryFile = fileUtils.parseFileInfo(changeFiles[j], "inquiry/");

                            updateInquiryFile.setInquiryNo(inquiryDTO.getInquiryNo());
                            updateInquiryFile.setInquiryFileNo(originFileList.get(i).getInquiryFileNo());
                            updateInquiryFile.setInquiryFileStatus("U");

                            uFileList.add(updateInquiryFile);
                        }
                    }
                } else if (originFileList.get(i).getInquiryFileStatus().equals("D")) {
                    InquiryFileDTO deleteInquiryFile = new InquiryFileDTO();

                    deleteInquiryFile.setInquiryNo(inquiryDTO.getInquiryNo());
                    deleteInquiryFile.setInquiryFileNo(originFileList.get(i).getInquiryFileNo());
                    deleteInquiryFile.setInquiryFileStatus("D");

                    uFileList.add(deleteInquiryFile);
                }
            }

            if (uploadFiles.length > 0) {
                for (int i = 0; i < uploadFiles.length; i++) {
                    if (!uploadFiles[i].getOriginalFilename().equals("") &&
                            uploadFiles[i].getOriginalFilename() != null) {
                        InquiryFileDTO insertInquiryFile = fileUtils.parseFileInfo(uploadFiles[i], "inquiry/");

                        insertInquiryFile.setInquiryNo(inquiryDTO.getInquiryNo());
                        insertInquiryFile.setInquiryFileStatus("I");

                        uFileList.add(insertInquiryFile);
                    }
                }
            }

            inquiryService.updateInquiry(inquiryDTO, uFileList);

            Map<String, String> returnMap = new HashMap<>();

            returnMap.put("msg", "정상적으로 수정되었습니다.");

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setErrorCode(701);
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/inquiry/{inquiryNo}")
    public ResponseEntity<?> deleteInquiry(@PathVariable("inquiryNo") Long inquiryNo) {
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        try {
            inquiryService.deleteInquiry(inquiryNo);

            Map<String, String> returnMap = new HashMap<>();

            returnMap.put("msg", "정상적으로 삭제되었습니다.");

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            response.setErrorCode(801);
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/inquiry-cnt/{inquiryNo}")
    public void updateInquiryCnt(@PathVariable("inquiryNo") Long inquiryNo,
                                 HttpServletResponse response) {
        try {
            inquiryService.updateInquiryCnt(inquiryNo);

            response.sendRedirect("/inquiry/inquiry/" + inquiryNo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/save-inquiry-list")
    public ResponseEntity<?> saveInquiryList(@RequestParam("changeRows") String changeRows) {
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        try {
            List<Map<String, String>> changeRowsList = new ObjectMapper().readValue(changeRows,
                    new TypeReference<List<Map<String, String>>>() {
                    });

            inquiryService.saveInquiryList(changeRowsList);

            Map<String, String> returnMap = new HashMap<>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            response.setErrorCode(801);
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }
}
