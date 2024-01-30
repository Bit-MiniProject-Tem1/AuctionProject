package com.bit.auction.user.repository;

import com.bit.auction.user.entity.Inquiry;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
@Transactional
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {

}
