package com.bit.auction.goods.repository.impl;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.repository.AuctionImgRepository;
import com.bit.auction.goods.repository.AuctionRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bit.auction.goods.entity.QAuction.auction;
import static com.bit.auction.goods.entity.QAuctionImg.auctionImg;

@Repository
@RequiredArgsConstructor
public class AuctionRepositoryImpl implements AuctionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;
    private final AuctionImgRepository auctionImgRepository;

    @Override
    @Transactional
    public void saveOne(Auction auction) {
        if (auction.getId() == null || auction.getId() == 0) {
            em.persist(auction);
        } else {
            if (auction.getAuctionImgList() != null || auction.getAuctionImgList().size() != 0) {
                auctionImgRepository.updateRepresentativeImg(auction);
            }
            em.merge(auction);
        }
    }

    @Override
    public Page<Auction> searchAll(Pageable pageable, Long categoryId, List<Long> subCategoryIdList, String sortOption, List<String> targetList, List<Character> statusList) {
        List<Auction> auctionList = jpaQueryFactory
                .selectFrom(auction)
                .where(eqCategoryId(categoryId, subCategoryIdList),
                        eqTarget(targetList),
                        eqStatus(statusList))
                .orderBy(auctionSort(sortOption))
                .fetch();

        auctionList.forEach(a -> {
            String url = jpaQueryFactory
                    .select(auctionImg.fileUrl)
                    .from(auctionImg)
                    .where(auctionImg.auction.id.eq(a.getId())
                            .and(auctionImg.isRepresentative.eq(true))
                    )
                    .fetchOne();

            a.representativeImgUrl(url);
        });

        long totalCnt = auctionList.size();

        return new PageImpl<>(auctionList, pageable, totalCnt);
    }

    @Override
    public Page<Auction> searchMyAuctionList(Pageable pageable, String regUserId, List<Character> statusList) {
        List<Auction> auctionList = jpaQueryFactory
                .selectFrom(auction)
                .where(auction.regUserId.eq(regUserId).and(eqStatus(statusList)))
                .fetch();

        auctionList.forEach(a -> {
            String url = jpaQueryFactory
                    .select(auctionImg.fileUrl)
                    .from(auctionImg)
                    .where(auctionImg.auction.id.eq(a.getId())
                            .and(auctionImg.isRepresentative.eq(true))
                    )
                    .fetchOne();

            a.representativeImgUrl(url);
        });

        // long totalCnt = jpaQueryFactory
        //         .select(auction.count())
        //         .where(auction.regUserId.eq(regUserId).and(eqStatus(statusList)))
        //         .from(auction)
        //         .fetchOne();

        long totalCnt = auctionList.size();

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

    private OrderSpecifier<?> auctionSort(String sortOption) {
        if (sortOption != null) {
            switch (sortOption) {
                case "byViews":
                    return new OrderSpecifier(Order.DESC, auction.view);
                case "byRegistration":
                    return new OrderSpecifier(Order.ASC, auction.regDate);
                case "byClosingSoon":
                    return new OrderSpecifier(Order.ASC, auction.endDate);
                case "byLowPrice":
                    return new OrderSpecifier(Order.ASC, auction.currentBiddingPrice);
                case "byHighPrice":
                    return new OrderSpecifier(Order.DESC, auction.currentBiddingPrice);
                // case "byMostBids":
                //     return new OrderSpecifier(Order.DESC, auction.bidding 카운트 뽑기);
                // case "byMostFavorite":
                //     return new OrderSpecifier(Order.DESC, auction.favorite 수);
                default:
                    break;
            }
        }
        return new OrderSpecifier(Order.DESC, auction.view);
    }
}
