package com.bit.auction.admin.repository;

import com.bit.auction.admin.entity.Faq;
import com.bit.auction.user.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberInfo_InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByInquiryWriter(String userId);

}
