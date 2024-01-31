package com.bit.auction.user.repository;

import com.bit.auction.user.dto.FaqAttachedFileDTO;
import com.bit.auction.user.entity.Faq;
import com.bit.auction.user.entity.FaqAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FaqAttachedFileRepository extends JpaRepository<FaqAttachedFile, Long> {
//    List<FaqAttachedFileDTO> findByFaqId(String faqId);

//    public List<FaqAttachedFileDTO> getFaqAttachedFileList(String faqId);

    Optional<FaqAttachedFile> findByFileIdAndFaqFaqId(Long fileId, Long faqId);

    FaqAttachedFile findByFaqFaqIdAndFileId(Long faqId, Long fileId);
}
