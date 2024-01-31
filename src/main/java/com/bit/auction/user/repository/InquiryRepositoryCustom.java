package com.bit.auction.user.repository;

import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InquiryRepositoryCustom {
    Page<Inquiry> searchAll(Pageable pageable, InquiryDTO inquiryDTO);

    Optional<Inquiry> searchOne(Long inquiryNo);

    void saveOne(Inquiry inquiry);

    void deleteOne(Inquiry inquiry);
}
