package com.bit.auction.goods.controller;

import com.bit.auction.common.CkEditorImageUtils;
import com.bit.auction.common.FileUtils;
import com.bit.auction.common.dto.ResponseDTO;
import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.AuctionImgDTO;
import com.bit.auction.goods.dto.CategoryDTO;
import com.bit.auction.goods.dto.DescriptionImgDTO;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.CategoryService;
import com.bit.auction.user.entity.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final CkEditorImageUtils ckEditorImageUtils;
    private final AuctionService auctionService;
    private final CategoryService categoryService;
    private final FileUtils fileUtils;
    private List<String> temporaryImage = new ArrayList<>();

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

    @GetMapping("/goods/{id}")
    public ModelAndView getGoods(@PathVariable("id") Long categoryId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        List<CategoryDTO> categoryList = categoryService.getTopCategoryList();
        mav.addObject("topCategoryList", categoryList);

        AuctionDTO auctionDTO = auctionService.getAuctionGoods(categoryId);
        if (auctionDTO == null) {
            mav.setViewName("auction/loadFail.html");

            return mav;
        }
        auctionService.updateView(auctionDTO.getId(), request, response);

        mav.addObject("getGoods", auctionDTO);
        mav.setViewName("auction/getAuction.html");

        return mav;
    }

    @GetMapping("/reg-goods")
    public ModelAndView getMyAuction(@RequestParam(required = false) String status,
                                     @PageableDefault(page = 0, size = 10) Pageable pageable) {
        ModelAndView mav = new ModelAndView();

        String regUserId = "kim";

        mav.addObject("auctionList", auctionService.getMyAuctionList(pageable, regUserId, status));
        mav.setViewName("user/mypage/getMyAuctionList.html");

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView registerAuctionView() {
        ModelAndView mav = new ModelAndView();

        List<CategoryDTO> categoryList = categoryService.getTopCategoryList();
        mav.addObject("topCategoryList", categoryList);

        mav.setViewName("auction/registerAuction.html");

        return mav;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAuction(AuctionDTO auctionDTO,
                                             @RequestParam(value = "uploadFiles", required = false) MultipartFile[] uploadFiles,
                                             @RequestParam(value = "topCategory", required = false) Long topCategoryId,
                                             @RequestParam(value = "subCategory", required = false) Long subCategoryId,
                                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        String representativeImgName = auctionDTO.getRepresentativeImgName();
        try {
            List<AuctionImgDTO> auctionImgDTOList = new ArrayList<>();
            for (MultipartFile file : uploadFiles) {
                if (file.getOriginalFilename() != null &&
                        !file.getOriginalFilename().equals("")) {
                    AuctionImgDTO auctionImgDTO = fileUtils.parseFileInfo(file, "auction/", representativeImgName);
                    auctionImgDTOList.add(auctionImgDTO);
                }
            }

            Long categoryId = topCategoryId;
            if (subCategoryId != null) {
                categoryId = subCategoryId;
            }

            auctionDTO.setAuctionImgDTOList(auctionImgDTOList);

            auctionService.removeDescriptionImg(auctionDTO.getDescription(), auctionDTO.getOriginDescription(), temporaryImage);
            temporaryImage.clear();

            auctionService.setAuction(auctionDTO, categoryId, customUserDetails.getUser());

            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("msg", "정상적으로 입력되었습니다.");

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorCode(605);
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/goods-update/{id}")
    public ModelAndView updateGoods(@PathVariable("id") Long categoryId) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("getGoods", auctionService.getAuctionGoods(categoryId));

        List<CategoryDTO> categoryList = categoryService.getTopCategoryList();
        mav.addObject("topCategoryList", categoryList);

        mav.setViewName("auction/updateAuction.html");

        return mav;
    }

    @GetMapping("/recent-items")
    public List<AuctionDTO> updateGoods(@RequestParam("recentItems") String recentItems) {

        recentItems = recentItems.replace("[", "");
        recentItems = recentItems.replace("]", "");
        recentItems = recentItems.replace("\"", "");

        String[] itemsArray = recentItems.split(",");

        long[] itemsIds = new long[itemsArray.length];

        for (int i = 0; i < itemsArray.length; i++) {
            itemsIds[i] = Long.parseLong(itemsArray[i]);
        }

        List<AuctionDTO> auctionDTOList = new ArrayList<>();

        for (int i = 0; i < itemsIds.length; i++) {

            auctionDTOList.add(auctionService.getAuctionGoods(itemsIds[i]));
        }

        return auctionDTOList;
    }

    @PutMapping("/goods-update")
    public ResponseEntity<?> updateAuction(AuctionDTO auctionDTO,
                                           @RequestParam(value = "uploadFiles", required = false) MultipartFile[] uploadFiles,
                                           @RequestParam(value = "topCategory", required = false) Long topCategoryId,
                                           @RequestParam(value = "subCategory", required = false) Long subCategoryId,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        String representativeImgName = auctionDTO.getRepresentativeImgName();
        try {
            List<AuctionImgDTO> auctionImgDTOList = new ArrayList<>();
            for (MultipartFile file : uploadFiles) {
                if (file.getOriginalFilename() != null &&
                        !file.getOriginalFilename().equals("")) {
                    AuctionImgDTO auctionImgDTO = fileUtils.parseFileInfo(file, "auction/", representativeImgName);
                    auctionImgDTOList.add(auctionImgDTO);
                }
            }
            Long categoryId = topCategoryId;
            if (auctionDTO.getCategoryId() == null) {
                if (subCategoryId != null) {
                    categoryId = subCategoryId;
                }
            } else {
                categoryId = auctionDTO.getCategoryId();
            }

            if (auctionDTO.getAuctionImgDTOList() != null || auctionDTO.getRepresentativeImgName() != null) {
                auctionDTO.setAuctionImgDTOList(auctionImgDTOList);
            }

            auctionService.removeDescriptionImg(auctionDTO.getDescription(), auctionDTO.getOriginDescription(), temporaryImage);
            temporaryImage.clear();

            auctionService.setAuction(auctionDTO, categoryId, customUserDetails.getUser());

            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("msg", "정상적으로 수정되었습니다.");

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorCode(605);
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> updateAuction(@RequestParam("id") Long id) {
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        try {
            auctionService.cancelAuction(id);

            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("msg", "경매가 취소되었습니다.");

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorCode(605);
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/goods-list")
    public ModelAndView getGoodsList(@RequestParam(required = false) Map<String, Object> paramMap,
                                     @RequestParam(required = false) String sort,
                                     @PageableDefault(page = 0, size = 12) Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auction/getAuctionList.html");

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

        List<Character> statusList = new ArrayList<>('S');
        if (paramMap.get("closing") != null) {
            statusList.add(paramMap.get("closing").toString().charAt(0));
        }

        if (paramMap.get("regUser") != null) {
            String regUserId = paramMap.get("regUser").toString();
            mav.addObject("topCategoryName", regUserId);
            mav.addObject("auctionList", auctionService.getMyAuctionList(pageable, regUserId, "S"));
            return mav;
        }

        if (paramMap.get("category") == null && paramMap.get("subCategory") == null) {
            mav.addObject("topCategoryName", "전체");
            Page<AuctionDTO> auctionPage = auctionService.getAuctionList(pageable, null, "all", sort, targetList, statusList);
            mav.addObject("auctionList", auctionPage);
        } else {
            Long categoryId = Long.valueOf(String.valueOf(paramMap.get("category")));
            mav.addObject("topCategoryName", categoryService.getCategoryName(categoryId));

            if (paramMap.get("subCategory") != null) {
                Long subCategoryId = Long.valueOf(String.valueOf(paramMap.get("subCategory")));
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, subCategoryId, "sub", sort, targetList, statusList));
            } else if (paramMap.get("etc") != null) {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, categoryId, "etc", sort, targetList, statusList));
            } else {
                mav.addObject("auctionList", auctionService.getAuctionList(pageable, categoryId, "top", sort, targetList, statusList));
            }
        }

        return mav;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(required = false) Map<String, Object> paramMap,
                               @PageableDefault(page = 0, size = 12) Pageable pageable,
                               @RequestParam(required = false) String searchQuery) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("auction/getAuctionList.html");

        List<CategoryDTO> categoryList = categoryService.getTopCategoryList();
        mav.addObject("topCategoryList", categoryList);

        if (paramMap.get("category") == null) {
            mav.addObject("categoryList", categoryList);
        } else {
            categoryList = categoryService.searchSubCategoryList(Long.valueOf(String.valueOf(paramMap.get("category"))));
            mav.addObject("categoryList", categoryList);
        }

        List<Character> statusList = new ArrayList<>();

        Page<AuctionDTO> auctionDTOList = auctionService.searchAuctions(pageable, searchQuery, statusList);
        if (auctionDTOList.getTotalElements() != 0) {
            // 검색 결과가 있으면 전체 항목에 포함된 항목이라면 추가
            mav.addObject("auctionList", auctionDTOList);
            mav.addObject("topCategoryName", "검색 결과");
        } else {
            // 검색 결과가 없으면 전체 항목을 보여주고 메시지 추가
            mav.addObject("searchMessage", "검색 결과가 없습니다. 전체 항목의 제품을 보여드립니다.");
            mav.addObject("showAlertValue", true);
            Page<AuctionDTO> auctionPage = auctionService.getAuctionList(pageable, null, "all", null, null, statusList);
            mav.addObject("auctionList", auctionPage);
        }
        System.out.println(mav);
        return mav;
    }

    @PostMapping(value = "/img/upload")
    public ModelAndView image(MultipartHttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        MultipartFile uploadFile = request.getFile("upload");

        DescriptionImgDTO descriptionImgDTO = ckEditorImageUtils.parseFileInfo(uploadFile, "description/");

        temporaryImage.add(descriptionImgDTO.getFileUrl());

        mav.addObject("uploaded", true);
        mav.addObject("url", descriptionImgDTO.getFileUrl());
        return mav;
    }

    @PutMapping("/img/notSave")
    public ResponseEntity<String> handleNotSaveRequest() {
        try {
            auctionService.removeDescriptionImg(temporaryImage);
            temporaryImage.clear();
            return ResponseEntity.ok().body("Request processed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
