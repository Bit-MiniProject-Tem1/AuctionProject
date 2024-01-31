package com.bit.auction.user.repository;

import com.bit.auction.user.entity.InquiryFile;
import com.bit.auction.user.entity.InquiryFileId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryFileRepository extends JpaRepository<InquiryFile, InquiryFileId> {
}
