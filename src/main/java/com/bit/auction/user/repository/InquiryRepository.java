package com.bit.auction.user.repository;

import com.bit.auction.user.entity.Inquiry;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {


    Optional<Inquiry> findByInquiryTitle(String inquiryTitle);

    Optional<Inquiry> findByInquiryTitleAndInquiryContent(String inquiryTitle, String inquiryContent);


    @Modifying
    @Query(value = "UPDATE Inquiry " +
            "           SET inquiryCnt = inquiryCnt + 1 " +
            "           WHERE inquiryNo = :inquiryNo ")
    void updateByInquiryNo(@Param("inquiryNo") Long inquiryNo);


    @Modifying
    @Query(value = "UPDATE Inquiry " +
            "           SET inquiryTitle = :#{#uInquiry.inquiryTitle}" +
            "           WHERE inquiryNo = :#{#uInquiry.inquiryNo}")
    void updateInquiryByInquiryNo(@Param("uInquiry") Inquiry uinquiry);
}
