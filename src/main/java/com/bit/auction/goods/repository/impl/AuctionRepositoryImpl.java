package com.bit.auction.goods.repository.impl;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.repository.AuctionRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bit.auction.goods.entity.QAuction.auction;

@Repository
@RequiredArgsConstructor
public class AuctionRepositoryImpl implements AuctionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;


    @Override
    public Page<Auction> searchAll(Pageable pageable, Long categoryId, List<Long> subCategoryIdList, List<String> targetList, List<Character> statusList) {
        List<Auction> auctionList = jpaQueryFactory
                .selectFrom(auction)
                .where(eqCategoryId(categoryId, subCategoryIdList),
                        eqTarget(targetList),
                        eqStatus(statusList))
                .fetch();

        long totalCnt = jpaQueryFactory
                .select(auction.count())
                .where(eqCategoryId(categoryId, subCategoryIdList),
                        eqTarget(targetList),
                        eqStatus(statusList))
                .from(auction)
                .fetchOne();

        return new PageImpl<>(auctionList, pageable, totalCnt);
    }

    @Override
    public List<Auction> findByAuctionNameContaining(String searchQuery, List<Character> statusList) {
        BooleanBuilder whereConditions = new BooleanBuilder()
                .and(auction.title.containsIgnoreCase(searchQuery))
                .and(eqStatus(statusList));

        return jpaQueryFactory
                .selectFrom(auction)
                .where(whereConditions)
                .fetch();
    }

    @Override
    public List<Auction> findByforResent() {

        return jpaQueryFactory
                .selectFrom(auction)
                .orderBy(auction.regDate.desc())
                .fetch();
    }

    @Override
    public List<Auction> findByforFinal() {

        return jpaQueryFactory
                .selectFrom(auction)
                .orderBy(auction.endDate.asc())
                .fetch();
    }

    private BooleanBuilder eqCategoryId(Long categoryId, List<Long> subCategoryIdList) {
        if (categoryId == 0L) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (subCategoryIdList != null) {
            booleanBuilder.or(auction.category.id.eq(categoryId));
            for (Long subCategoryId : subCategoryIdList) {
                booleanBuilder.or(auction.category.topCategoryId.eq(subCategoryId));
            }
        } else {
            booleanBuilder.and(auction.category.id.eq(categoryId));
        }

        return booleanBuilder;
    }

    private BooleanBuilder eqTarget(List<String> targetList) {
        if (targetList == null) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String target : targetList) {
            booleanBuilder.or(auction.target.eq(target));
        }
        return booleanBuilder;
    }

    private BooleanBuilder eqStatus(List<Character> statusList) {
        if (statusList == null) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (Character status : statusList) {
            booleanBuilder.or(auction.status.eq(status));
        }
        return booleanBuilder;
    }


}
