package com.bit.auction.goods.repository.impl;

import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.AuctionImg;
import com.bit.auction.goods.repository.AuctionImgRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bit.auction.goods.entity.QAuctionImg.auctionImg;

@Repository
@RequiredArgsConstructor
public class AuctionImgRepositoryImpl implements AuctionImgRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    @Transactional
    public void updateRepresentativeImg(Auction auction) {
        List<AuctionImg> auctionImgList = jpaQueryFactory
                .selectFrom(auctionImg)
                .where(auctionImg.auction.id.eq(auction.getId()))
                .fetch();

        AtomicInteger count = new AtomicInteger();
        auctionImgList.forEach(img -> {
            if (img.isRepresentative()) {
                count.getAndIncrement();
            }

            if (!img.getFileName().equals(auction.getRepresentativeImgName())) {
                jpaQueryFactory
                        .update(auctionImg)
                        .set(auctionImg.isRepresentative, false)
                        .where(auctionImg.id.eq(img.getId()))
                        .execute();
                em.clear();
                em.flush();
            } else {
                if (!img.isRepresentative()) {
                    jpaQueryFactory
                            .update(auctionImg)
                            .set(auctionImg.isRepresentative, true)
                            .where(auctionImg.id.eq(img.getId()))
                            .execute();
                    em.clear();
                    em.flush();
                }
            }

        });

        if (count.get() == 0) {
            jpaQueryFactory
                    .update(auctionImg)
                    .set(auctionImg.isRepresentative, true)
                    .where(auctionImg.id.eq(0L))
                    .execute();
            em.clear();
            em.flush();
        }
    }
}
