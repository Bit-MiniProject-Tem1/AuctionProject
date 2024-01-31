package com.bit.auction.user.service;

import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.InquiryFileDTO;
import com.bit.auction.user.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InquiryService {

    void insertInquiry(InquiryDTO inquiryDTO);

    InquiryDTO getInquiry(Long inquiryNo);

    void updateInquiry(InquiryDTO inquiryDTO, List<InquiryFileDTO> uFileList);

    void deleteInquiry(Long inquiryNo);

    Page<InquiryDTO> getInquiryList(Pageable pageable, InquiryDTO inquiryDTO);

    Page<InquiryDTO> findAll(Pageable pageable);

    void save(InquiryDTO inquiryDTO);

    InquiryDTO findById(Long inquiryNo);

    InquiryDTO findByInquiryTitle(String inquiryTitle);

    InquiryDTO findByInquiryTitleAndInquiryContent(String InquiryTitle, String InquiryContent);

    List<InquiryDTO> findByInquiryTitleContaining(String searchKeyword);

    List<InquiryDTO> search(String searchKeyword, String searchCondition);

    void updateInquiryCnt(Long inquiryNo);

    void saveInquiryList(List<Map<String, String>> changeRowsList);
}
