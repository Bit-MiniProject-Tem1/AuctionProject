package com.bit.auction.goods.controller;

import com.bit.auction.common.dto.ResponseDTO;
import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.LikeCntDTO;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.LikeCntService;
import com.bit.auction.user.entity.CustomUserDetails;
import jakarta.annotation.Nullable;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeCntController {

    private final LikeCntService likeCntService;
    private final AuctionService auctionService;

    @PostMapping("/add/{id}")
    public ResponseEntity<?> addLike(@PathVariable("id") Long auctionId,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

        try {
            likeCntService.insertLike(customUserDetails.getUser(), auctionId);
            Map<String, Object> returnMap = new HashMap<>();

            List<AuctionDTO> likeList = auctionService.findByUserId(customUserDetails.getUser().getId());
            long likeCnt = likeCntService.findByUserIdAndAuctionId(customUserDetails.getUser().getId(), auctionId);
            long likeSum = likeCntService.findByAuctionId(auctionId);

            returnMap.put("likeCnt", String.valueOf(likeCnt));
            returnMap.put("likeSum", String.valueOf(likeSum));
            returnMap.put("likeList", likeList);

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorCode(1);
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable("id") Long categoryId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

        try {
            likeCntService.deleteLike(customUserDetails.getUser(), categoryId);

            Map<String, Object> returnMap = new HashMap<>();

            long likeCnt = likeCntService.findByUserIdAndAuctionId(customUserDetails.getUser().getId(), categoryId);
            long likeSum = likeCntService.findByAuctionId(categoryId);
            List<AuctionDTO> likeList = auctionService.findByUserId(customUserDetails.getUser().getId());

            returnMap.put("likeCnt", String.valueOf(likeCnt));
            returnMap.put("likeSum", String.valueOf(likeSum));
            returnMap.put("likeList", likeList);

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorCode(1);
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/getLikeList")
    public ResponseEntity<?> getLikeList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ResponseDTO<AuctionDTO> responseDTO = new ResponseDTO<>();

        try {
            List<AuctionDTO> likeList = auctionService.findByUserId(customUserDetails.getUser().getId());

            responseDTO.setItems(likeList);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorCode(1);
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
