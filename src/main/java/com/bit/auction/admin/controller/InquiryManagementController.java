package com.bit.auction.admin.controller;

import com.bit.auction.common.FileUtils;
import com.bit.auction.admin.dto.FaqAttachedFileDTO;
import com.bit.auction.admin.dto.FaqDTO;
import com.bit.auction.admin.repository.FaqRepository;
import com.bit.auction.admin.service.FaqService;
import com.bit.auction.user.service.InquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.*;
import java.net.URL;


@Slf4j
@Controller
@RequiredArgsConstructor
public class InquiryManagementController {
    private final FaqService faqService;
    private final InquiryService inquiryService;
    private final FileUtils fileUtils;
    private final FaqRepository faqRepository;

    @Value("${ncp.bucket}")
    private String bucketName;

    @Value("${ncp.endPoint}")
    private String storageUrl;

/*    @GetMapping("/inquiryManagement")
    public String inquiryManagement() {
        return "/admin/inquiryManagement";
    }*/

    @GetMapping(value = {"/admin/inquiry-management_main", "/admin/inquiry-management_search"})
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


    @GetMapping("/admin/faq_add")
    public String faqAddView() {
        return "/admin/faq_add";
    }

    @PostMapping("/admin/faq_add")
    public String faqSave(FaqDTO faqDTO, RedirectAttributes redirectAttributes, MultipartFile[] uploadFiles, HttpServletRequest request, HttpServletResponse response) {

//        String fileNames = "";

        try {
            List<FaqAttachedFileDTO> faqAttachedFileDTOList = new ArrayList<>();

            for (MultipartFile file : uploadFiles) {
                if (file.getOriginalFilename() != null &&
                        !file.getOriginalFilename().equals("")) {
                    FaqAttachedFileDTO faqAttachedFileDTO = fileUtils.parseFaqAttachedFileInfo(file, "faq/");

                    faqAttachedFileDTO.setFaqId(faqDTO.getFaqId());

                    faqAttachedFileDTOList.add(faqAttachedFileDTO);

//                    fileNames = fileNames + faqAttachedFileDTO.getFileName() + "  ";
                }
            }
//            fileNames.trim();
            faqDTO.setFaqAttachedFileDTOList(faqAttachedFileDTOList);
            faqService.insertFaq(faqDTO);
            Map<String, String> returnMap = new HashMap<>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            response.setStatus(HttpStatus.OK.value());

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
            response.setStatus(HttpStatus.BAD_REQUEST.value());

            faqService.insertFaq(faqDTO);
        }

        FaqDTO addedFaqDTO = faqService.findTopByOrderByFaqIdDesc();

        redirectAttributes.addFlashAttribute("faqDTO", addedFaqDTO);
//        redirectAttributes.addFlashAttribute("fileNames", fileNames);
        redirectAttributes.addAttribute("faqId", addedFaqDTO.getFaqId());
        redirectAttributes.addAttribute("status", true);
        redirectAttributes.addAttribute("requestUrl", request.getRequestURL().toString());

        log.info("############ requestUrl : " + request.getRequestURL().toString());

        return "redirect:/admin/inquiry-management/faq_detail/faq-{faqId}";
    }

/*    @GetMapping("/faq_add_result")
    public String faqAddResult() {
        return "/admin/faq_add_result";
    }*/

    @GetMapping("/admin/inquiry-management/faq_delete/faq-{faqId}")

    public String faqDelete(@PathVariable("faqId") Long faqId , RedirectAttributes redirectAttributes) {
        log.info("########## faqDTO.getFaqId() = {}", faqId);

        //redirectAttributes.addFlashAttribute("faqDTO", faqService.getFaq(faqId));
        faqService.deleteFaq(faqId);
        return "redirect:/admin/inquiry-management_main";
    }


    @GetMapping("/admin/inquiry-management/faq_detail/faq-{faqId}")
    public ModelAndView getBoard(@PathVariable("faqId") Long faqId, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();


        mv.addObject("faqDTO", faqService.updateViewsCount(faqId));
        mv.addObject("requestUrl", request.getRequestURL().toString());
        mv.setViewName("/admin/inquiryManagement_faqDetail.html");

        return mv;
    }


    /*=================================== FAQ수정 ===================================*/

    @PostMapping("/admin/faq_modification/faq-{faqId}")
    public String faqModification(@PathVariable("faqId") Long faqId, @ModelAttribute FaqDTO faqDTO, RedirectAttributes redirectAttributes, MultipartFile[] uploadFiles, HttpServletRequest request, HttpServletResponse response) {

//        String fileNames = "";

        try {
            List<FaqAttachedFileDTO> faqAttachedFileDTOList = new ArrayList<>();

            for (MultipartFile file : uploadFiles) {
                if (file.getOriginalFilename() != null &&
                        !file.getOriginalFilename().equals("")) {
                    FaqAttachedFileDTO faqAttachedFileDTO = fileUtils.parseFaqAttachedFileInfo(file, "faq/");

                    faqAttachedFileDTO.setFaqId(faqDTO.getFaqId());

                    faqAttachedFileDTOList.add(faqAttachedFileDTO);

//                    fileNames = fileNames + faqAttachedFileDTO.getFileName() + "  ";
                }
            }
//            fileNames.trim();
            faqDTO.setFaqAttachedFileDTOList(faqAttachedFileDTOList);
//            faqService.updateFaq(faqDTO.getFaqId(), faqDTO);
            faqRepository.save(faqDTO.toEntity());

            Map<String, String> returnMap = new HashMap<>();


            returnMap.put("msg", "정상적으로 저장되었습니다.");

            response.setStatus(HttpStatus.OK.value());

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
            response.setStatus(HttpStatus.BAD_REQUEST.value());

//            faqService.updateFaq(faqDTO.getFaqId(), faqDTO);
//            faqRepository.save(faqDTO.toEntity());
        }

//      ===========================================================================
        if (faqDTO.getFaqAttachedFileDTOList() == null) {
            faqDTO.setFaqAttachedFileDTOList(new ArrayList<>());
        }

        faqService.updateFaq(faqId, faqDTO);


        redirectAttributes.addFlashAttribute("faqId", faqId);
        redirectAttributes.addFlashAttribute("faqDTO", faqDTO);
//        redirectAttributes.addFlashAttribute("fileNames", fileNames);
        redirectAttributes.addAttribute("itemId", faqDTO.getFaqId());
        redirectAttributes.addAttribute("status", true);
        redirectAttributes.addAttribute("requestUrl", request.getRequestURL().toString());

        log.info("############ requestUrl : " + request.getRequestURL().toString());

        return "redirect:/admin/inquiry-management/faq_detail/faq-{faqId}";
    }


    @GetMapping("/admin/faq_modification/faq-{faqId}")
    public ModelAndView faqModificationView(@PathVariable("faqId") Long faqId, HttpServletRequest request) throws IOException {
        ModelAndView mv = new ModelAndView();
        FaqDTO faqDTO = faqService.findById(faqId);

        List<FaqAttachedFileDTO> faqAttachedFileDTOList = faqDTO.getFaqAttachedFileDTOList();
//        List<String> base64DataList = new ArrayList<>();
        List<Map<String, Object>> fileDataList = new ArrayList<>();

        log.info("1");

        try {
            for (FaqAttachedFileDTO faqAttachedFileDTO : faqAttachedFileDTOList) {

                log.info("2 >>> faqAttachedFileDTO.getFilePath() >>> {}", faqAttachedFileDTO.getFilePath());
                byte[] fileData = readWebFile(faqAttachedFileDTO.getFilePath());
                log.info("3 >>> fileData >>> {}", fileData);
//                String base64Data = Base64.getEncoder().encodeToString(fileData);
                Map<String, Object> fileDataMap = new HashMap<>();
                fileDataMap.put("name", faqAttachedFileDTO.getFileName());
                fileDataMap.put("type", faqAttachedFileDTO.getFileContentType(faqAttachedFileDTO.getFileName()));
                fileDataMap.put("data", Base64.getEncoder().encodeToString(fileData));

                log.info("4");
//                base64DataList.add(base64Data);
                fileDataList.add(fileDataMap);
                log.info("5");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mv.addObject("faqDTO", faqDTO);
        mv.addObject("category", faqDTO.getCategory());
//        mv.addObject("base64DataList", base64DataList);
        mv.addObject("fileDataList", fileDataList);
        mv.addObject("requestUrl", request.getRequestURL().toString());
        mv.setViewName("/admin/faq_modification.html");
        log.info("6");

        return mv;
    }


    private static byte[] readWebFile(String fileUrl) throws IOException {
        String encodedUrl = UriComponentsBuilder.fromHttpUrl(fileUrl).build().encode().toUriString();
        URL url = new URL(encodedUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try (InputStream inputStream = httpURLConnection.getInputStream()) {
            return readInputStream(inputStream);
        } finally {
            httpURLConnection.disconnect();
        }
    }

    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        return outputStream.toByteArray();
    }

    /*private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        StringBuilder content = new StringBuilder();

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            content.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
        }

        return content.toString().getBytes(StandardCharsets.UTF_8);
    }*/


}
