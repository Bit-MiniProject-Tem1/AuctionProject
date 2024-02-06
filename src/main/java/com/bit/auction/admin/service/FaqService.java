package com.bit.auction.admin.service;

import com.bit.auction.admin.dto.FaqAttachedFileDTO;
import com.bit.auction.admin.dto.FaqDTO;
import com.bit.auction.admin.entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FaqService {
    void insertFaq(FaqDTO faqDTO);

    FaqDTO getFaq(Long faqId);

    FaqDTO updateViewsCount(Long faqId);

    void updateFaq(FaqDTO faqDTO);

    void updateFaq(Long faqId, FaqDTO faqDTO);

    void deleteFaq(Long faqId);

    Page<FaqDTO> getFaqList(Pageable pageable, FaqDTO faqDTO);

//    List<FaqAttachedFileDTO> getFaqAttachedFileList(Long faqId);

    Page<FaqDTO> findAll(Pageable pageable);

    void save(FaqDTO faqDTO);

    Faq save(Faq faq);

    FaqAttachedFileDTO getFaqAttachedFileDTO(FaqAttachedFileDTO faqAttachedFileDTO);

    FaqAttachedFileDTO getFaqAttachedFileDTO(Long faqId, Long fileId);

    FaqDTO findById(Long faqId);
//
    FaqDTO findTopByOrderByFaqIdDesc();

    List<Faq> findAllByFaqId(FaqDTO faqDTO);
    void deleteById(Long faqId);

    //---------------------------------------------------------------------------

    List<FaqDTO> findByCategory(Pageable pageable, String category);

    List<FaqDTO> findByTitleContaining(String searchKeyword);

    List<FaqDTO> findByContentContaining(String searchKeyword);

    List<FaqDTO> findByCategoryAndTitleContaining(String category, String searchKeyword);

    List<FaqDTO> findByCategoryAndContentContaining(String category, String searchKeyword);

    List<FaqDTO> findByCategoryAndTitleContainingOrContentContaining(String category, String searchKeyword);

    List<FaqDTO> search(String category, String searchCondition, String searchKeyword);

//    List<FaqAttachedFileDTO> findByFaqId(Long faqId);
}
