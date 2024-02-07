package com.bit.auction.user.repository.impl;

import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.entity.Inquiry;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.InquiryRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.bit.auction.user.entity.QInquiry.inquiry;


@Repository
@RequiredArgsConstructor
public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public Page<Inquiry> searchAll(Pageable pageable, InquiryDTO inquiryDTO) {
        List<Inquiry> inquiryList = jpaQueryFactory
                .selectFrom(inquiry)
                .where(getSearch(inquiryDTO.getSearchCondition(), inquiryDTO.getSearchKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCnt = jpaQueryFactory
                .select(inquiry.count())
                .where(getSearch(inquiryDTO.getSearchCondition(), inquiryDTO.getSearchKeyword()))
                .from(inquiry)
                .fetchOne();

        return new PageImpl<>(inquiryList, pageable, totalCnt);

    }


    @Override
    public Optional<Inquiry> searchOne(Long inquiryNo) {
        Inquiry getInquiry = jpaQueryFactory
                .selectFrom(inquiry)
                .where(inquiry.inquiryNo.eq(inquiryNo))
                .fetchOne();

        return Optional.of(getInquiry);
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

    public BooleanBuilder getSearch(String searchCondition, String searchKeyword) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        User user = new User();

        // 검색어가 전달되지 않았을 때는 null을 리턴해서 where절이 실행되지 않도록
        if (searchKeyword == null || searchKeyword.isEmpty()) {
            return null;
        }


        if (searchCondition.equalsIgnoreCase("all")) {
            // boardTitle like '%searchKeyword%'
            // or boardContent like '%searchKeyword%'
            // or boardWriter like '%searchKeyword%'
            booleanBuilder.or(inquiry.inquiryTitle.containsIgnoreCase(searchKeyword));
            booleanBuilder.or(inquiry.inquiryWriter.containsIgnoreCase(searchKeyword));
            booleanBuilder.or(inquiry.inquiryType.containsIgnoreCase(searchKeyword));
            booleanBuilder.or(inquiry.inquiryAnswer.containsIgnoreCase(searchKeyword));
        } else if (searchCondition.equalsIgnoreCase("title")) {
            booleanBuilder.or(inquiry.inquiryTitle.containsIgnoreCase(searchKeyword));
        } else if (searchCondition.equalsIgnoreCase("writer")) {
            booleanBuilder.or(inquiry.inquiryWriter.containsIgnoreCase(searchKeyword));
        } else if (searchCondition.equalsIgnoreCase("type")) {
            booleanBuilder.or(inquiry.inquiryType.containsIgnoreCase(searchKeyword));
        } else if (searchCondition.equalsIgnoreCase("answer")) {
            booleanBuilder.or(inquiry.inquiryAnswer.containsIgnoreCase(searchKeyword));
        }

        return booleanBuilder;
    }
}
