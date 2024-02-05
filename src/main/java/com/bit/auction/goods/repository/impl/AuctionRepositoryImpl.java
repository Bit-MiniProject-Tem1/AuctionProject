package com.bit.auction.goods.repository.impl;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.repository.AuctionImgRepository;
import com.bit.auction.goods.repository.AuctionRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.bit.auction.goods.entity.QAuction.auction;
import static com.bit.auction.goods.entity.QAuctionImg.auctionImg;
import static com.bit.auction.goods.entity.QBidding.bidding;
import static com.bit.auction.goods.entity.QLikeCnt.likeCnt;

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
            if (auction.getAuctionImgList() != null) {
                auctionImgRepository.updateRepresentativeImg(auction);
            }
            em.merge(auction);
        }
    }

    @Override
    public Page<Auction> searchAll(Pageable pageable, List<Long> subCategoryIdList, String sortOption, List<String> targetList, List<Character> statusList) {
        List<Auction> auctionList;

        if (sortOption != null && sortOption.equals("byMostFavorite")) {
            auctionList = jpaQueryFactory
                    .selectFrom(auction)
                    .leftJoin(likeCnt).on(auction.id.eq(likeCnt.auction.id))
                    .groupBy(auction.id)
                    .where(eqCategoryId(subCategoryIdList),
                            eqTarget(targetList),
                            eqStatus(statusList))
                    .orderBy(Expressions.numberTemplate(Double.class, "coalesce({0}, {1})", likeCnt.auction.id.count(), 0).desc())
                    .fetch();
        } else {
            auctionList = jpaQueryFactory
                    .selectFrom(auction)
                    .where(eqCategoryId(subCategoryIdList),
                            eqTarget(targetList),
                            eqStatus(statusList))
                    .orderBy(auctionSort(sortOption))
                    .fetch();
        }

        auctionRepresentative(auctionList);

        long totalCnt = auctionList.size();

        return new PageImpl<>(auctionList, pageable, totalCnt);
    }

    @Override
    public Page<Auction> searchMyAuctionList(Pageable pageable, String regUserId, List<Character> statusList) {
        List<Auction> auctionList = jpaQueryFactory
                .selectFrom(auction)
                .where(auction.regUser.userId.eq(regUserId).and(eqStatus(statusList)))
                .fetch();

        auctionRepresentative(auctionList);

        long totalCnt = auctionList.size();

        return new PageImpl<>(auctionList, pageable, totalCnt);
    }

    private void auctionRepresentative(List<Auction> auctionList) {
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
    }

    @Override
    public Page<Auction> searchMyBiddingList(Pageable pageable, String userId) {
        List<Auction> auctionList = jpaQueryFactory
                .selectFrom(auction)
                .join(bidding)
                .on(auction.id.eq(bidding.auctionId).and(bidding.userId.eq(userId)))
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

    private BooleanBuilder eqCategoryId(List<Long> subCategoryIdList) {
        if (subCategoryIdList.get(0) == 0L) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (Long subCategoryId : subCategoryIdList) {
            booleanBuilder.or(auction.category.id.eq(subCategoryId));
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
            System.out.println(status);
            if (status.equals('E')) {
                booleanBuilder.or(
                        auction.status.eq('S').and(auction.endDate.lt(LocalDateTime.now()))
                                .or(auction.status.eq('E'))
                );
            } else if (status.equals('S')) {
                booleanBuilder.or(auction.status.eq('S').and(auction.endDate.gt(LocalDateTime.now())));
            } else if (status.equals('C')) {
                booleanBuilder.or(auction.status.eq(status));
            }
        }

        return booleanBuilder;
    }

    private OrderSpecifier<?> auctionSort(String sortOption) {
        if (sortOption != null) {
            switch (sortOption) {
                case "byViews":
                    return new OrderSpecifier<>(Order.DESC, auction.view);
                case "byRegistration":
                    return new OrderSpecifier<>(Order.ASC, auction.regDate);
                case "byClosingSoon":
                    return new OrderSpecifier<>(Order.ASC, auction.endDate);
                case "byLowPrice":
                    return new OrderSpecifier<>(Order.ASC, auction.currentBiddingPrice);
                case "byHighPrice":
                    return new OrderSpecifier<>(Order.DESC, auction.currentBiddingPrice);
                // case "byMostBids":
                //     return new OrderSpecifier(Order.DESC, auction.bidding 카운트 뽑기);
                default:
                    break;
            }
        }
        return new OrderSpecifier<>(Order.DESC, auction.view);
    }
}
