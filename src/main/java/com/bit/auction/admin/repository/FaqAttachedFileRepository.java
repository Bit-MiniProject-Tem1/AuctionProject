package com.bit.auction.admin.repository;

import com.bit.auction.admin.entity.Faq;
import com.bit.auction.admin.entity.FaqAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqAttachedFileRepository extends JpaRepository<FaqAttachedFile, Long> {
//    List<FaqAttachedFileDTO> findByFaqFaqId(String faqId);


    Optional<FaqAttachedFile> findByFileIdAndFaqFaqId(Long fileId, Long faqId);

    FaqAttachedFile findByFaqFaqIdAndFileId(Long faqId, Long fileId);

    void deleteByFaq(Faq faq);
}
