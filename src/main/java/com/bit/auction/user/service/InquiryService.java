package com.bit.auction.user.service;

import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.entity.Inquiry;

import java.util.List;

public interface InquiryService {

    void insertInquiry(InquiryDTO inquiryDTO);

    InquiryDTO getInquiry(Long inquiryNo);

    List<InquiryDTO> findAll();
}
