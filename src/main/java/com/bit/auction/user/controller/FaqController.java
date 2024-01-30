package com.bit.auction.user.controller;

import com.bit.auction.common.FileUtils;
import com.bit.auction.user.dto.FaqAttachedFileDTO;
import com.bit.auction.user.dto.FaqDTO;
import com.bit.auction.user.service.FaqService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;
    private final FileUtils fileUtils;

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



/*

    @GetMapping("/faq_search")
    public ModelAndView faqSearch(@PageableDefault(page = 0, size = 10) Pageable pageable, FaqDTO faqDTO, Model item) {
        ModelAndView mv = new ModelAndView();
*/
/*        String category = paramMap.get("category");
        String condition = paramMap.get("condition");
        String keyword = paramMap.get("keyword");

        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setCategory(category);
        faqDTO.setContent(condition);
        faqDTO.setSearchKeyword(keyword);*//*


        String category = faqDTO.getCategory();
        String condition = faqDTO.getSearchCondition();
        String keyword = faqDTO.getSearchKeyword();

        mv.addObject("faqList", faqService.getFaqList(pageable, faqDTO));
        mv.addObject("category", category == null ? "전체" : category);
        mv.addObject("searchCondition", condition == null ? "전체" : condition);
        mv.addObject("searchKeyword", keyword == null ? "" : keyword);

//        item.addAttribute()

        log.info("category : {} / searchCondition : {} / searchKeyword : {}", category, condition, keyword);
        mv.setViewName("/user/customer/faq_main.html");

        return mv;
    }
*/

    @GetMapping("/faq_detail")
    public String faqDetail() {
        return "/user/customer/faq_detail";
    }

    @GetMapping("/faq_detail/faq-{faqId}")
    public ModelAndView getBoard(@PathVariable("faqId") Long faqId) {
        ModelAndView mv = new ModelAndView();
        /*FaqDTO faqDTO = faqService.findById(faqId);
        faqService.updateViewsCount(faqId);*/

        mv.addObject("faqDTO", faqService.updateViewsCount(faqId));
        mv.setViewName("/user/customer/faq_detail.html");

        return mv;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]>fileDownload(FaqAttachedFileDTO faqAttachedFileDTO) throws IOException {
        FaqAttachedFileDTO downloadFile = faqService.getFaqAttachedFileDTO(faqAttachedFileDTO);

        return fileUtils.getObject(downloadFile.getFilePath());
    }

/*    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource>fileDownload(@PathVariable Long fileId) throws MalformedURLException {
        FaqAttachedFileDTO downloadFile = faqa.getFaqAttachedFileDTO(faqAttachedFileDTO);

        return fileUtils.getObject(downloadFile.getFilePath());
    }*/


    @GetMapping("/faq_add")
    public String faqAddView() {
        return "/user/customer/faq_add";
    }


/*

    @ResponseBody
    @PostMapping("/faq_add")
    public ResponseEntity<FaqDTO> faqSave(FaqDTO faqDTO) {

        faqService.insertFaq(faqDTO);

        log.info("### category : {}", faqDTO.getCategory());
        log.info("### title : {}", faqDTO.getTitle());
        log.info("### content : {}", faqDTO.getContent());

        return new ResponseEntity<>(faqDTO, HttpStatus.OK);
    }
*/

/*
    @PostMapping("/faq_add_t")
    public ResponseEntity<?> faqSaveT(FaqDTO faqDTO, RedirectAttributes redirectAttributes,
                          MultipartFile[] uploadFiles, HttpServletRequest request) {

        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        try {
            List<FaqAttachedFileDTO> faqAttachedFileDTOList = new ArrayList<>();

            for (MultipartFile file : uploadFiles) {
                if (file.getOriginalFilename() != null &&
                        !file.getOriginalFilename().equals("")) {
                    FaqAttachedFileDTO faqAttachedFileDTO = fileUtils.parseFileInfo(file, "faq/");

                    faqAttachedFileDTO.setFaqId(faqDTO.getFaqId());

                    faqAttachedFileDTOList.add(faqAttachedFileDTO);
                }
            }
            faqDTO.setFaqAttachedFileDTOList(faqAttachedFileDTOList);
            faqService.insertFaq(faqDTO);
            Map<String, String> returnMap = new HashMap<>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            if (faqDTO.getCategory().equals("")) {
                response.setErrorCode(602);
                response.setErrorMessage("카테고리를 입력하세요.");
            } else if (faqDTO.getTitle().equals("")) {
                response.setErrorCode(603);
                response.setErrorMessage("제목을 입력하세요.");
            } else if (faqDTO.getContent().equals("")) {
                response.setErrorCode(604);
                response.setErrorMessage("내용을 입력하세요.");
            } else {
                response.setErrorCode(605);
                response.setErrorMessage(e.getMessage());
            }
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }
*/


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
//        redirectAttributes.addAttribute("requestUrl", request.getRequestURL().toString());

        log.info("############ fileNames : " + fileNames);

        return "redirect:/faq_detail";
    }



/*
    @PostMapping("/faq_add")
    public String faqSave(FaqDTO faqDTO, RedirectAttributes redirectAttributes, MultipartFile[] uploadFiles, HttpServletRequest request) {
        faqService.insertFaq(faqDTO);

        redirectAttributes.addFlashAttribute("faqDTO", faqDTO);
        redirectAttributes.addAttribute("itemId", faqDTO.getFaqId());
        redirectAttributes.addAttribute("status", true);
//        redirectAttributes.addAttribute("requestUrl", request.getRequestURL().toString());

        return "redirect:/faq_detail";
    }
*/


/*

    @ResponseBody
    @RequestMapping("request-param-required")
    // 필수 파라미터 지정 --> required가 true이면 해당 파라미터 정보가 꼭 들어와야 한다.(없으면 오류 발생)
    //                     required가 false이면 해당 파라미터 정보가 들어오지 않아도 오류가 발생하지 않는다.
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = true) Integer age) {
        // 주의!! --> required를 false로 할 경우 int 타입의 파라미터는 값이 안들어오면 에러가 발생한다.
        //           int는 기본자료형이라서 null이 될 수 없기 때문..
        //           그러므로 (required = false) 속성을 사용해야 한다면 int 대신 null 처리가 가능한 객체형인 Integer 타입으로 사용해야 한다.
        log.info("username:{}, age:{}", username, age);

        return "ok";
    }
*/

/*
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }*/

}


