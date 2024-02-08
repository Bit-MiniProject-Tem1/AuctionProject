package com.bit.auction.admin.service;

import com.bit.auction.admin.dto.FaqDTO;
import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberManagementService {
    UserDTO getMember(Long id);

    Page<UserDTO> getMemberList(Pageable pageable, UserDTO userDTO);
    Page<UserDTO> findAll(Pageable pageable);

    UserDTO findById(Long id);

    List<InquiryDTO> findByInquiryWriter(String userId);

    List<BiddingDTO> findByUserId(String userId);

    String getAuctionTitle(Long id);
}
