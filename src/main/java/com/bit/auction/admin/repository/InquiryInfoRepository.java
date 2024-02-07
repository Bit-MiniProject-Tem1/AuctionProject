package com.bit.auction.admin.repository;

import com.bit.auction.user.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryInfoRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByInquiryWriter(String userId);
}
