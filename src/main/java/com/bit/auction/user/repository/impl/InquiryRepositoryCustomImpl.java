package com.bit.auction.user.repository.impl;

import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.entity.Inquiry;
import com.bit.auction.user.repository.InquiryRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public Page<Inquiry> searchAll(Pageable pageable, InquiryDTO inquiryDTO) {
        return null;
    }

    @Override
    public Optional<Inquiry> searchOne(Long inquiryNo) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public void saveOne(Inquiry inquiry) {
       if (inquiry.getInquiryNo() == 0) {
           em.persist(inquiry);
       } else {
           em.merge(inquiry);
       }
    }

    @Override
    public void deleteOne(Inquiry inquiry) {
        em.remove(inquiry);
    }
}
