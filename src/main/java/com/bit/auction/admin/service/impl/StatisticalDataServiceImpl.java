package com.bit.auction.admin.service.impl;

import com.bit.auction.admin.repository.AuctionInfoRepository;
import com.bit.auction.admin.repository.BiddingInfoRepository;
import com.bit.auction.admin.service.StatisticalDataService;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.Bidding;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class StatisticalDataServiceImpl implements StatisticalDataService {

    private final BiddingInfoRepository biddingRepository;
    private final AuctionInfoRepository auctionRepository;
    private final EntityManager entityManager;
    private final int monthsCnt = 6;

    @Override
    public String[] getStatisticalPeriod() {
        String[] monthList = new String[monthsCnt];
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");

        for(int i = 0; i < monthsCnt; i++) {
            monthList[i] =  currentDate.minusMonths(i).format(formatter);
        }

        return monthList;
    }

    @Override
    public int[] getBiddingCountList() {
        int[] biddingCountList = new int[monthsCnt];
        String fieldName = "date";

        for(int i = 0; i < monthsCnt; i++) {
            Specification<Bidding> spec = getEntityList(Bidding.class, i, fieldName);
            List<Bidding> biddingList = biddingRepository.findAll(spec);
            biddingCountList[i] = biddingList.size();
        }

        return biddingCountList;
    }

    @Override
    public int[] getAuctionCountList() {
        int[] auctionCountList = new int[monthsCnt];
        String fieldName = "regDate";

        for(int i = 0; i < monthsCnt; i++) {
            Specification<Auction> spec = getEntityList(Auction.class, i, fieldName);
            List<Auction> auctionList = auctionRepository.findAll(spec);
            auctionCountList[i] = auctionList.size();
        }

        return auctionCountList;
    }

    @Override
    public int[] getTotalPriceList() {
        int[] totalPriceList = new int[monthsCnt];
        String fieldName = "currentBiddingPrice";

        for(int i = 0; i < monthsCnt; i++) {
            totalPriceList[i] = getTotalPrice(Auction.class, i, fieldName);
        }

        return totalPriceList;
    }

    public <T> int getTotalPrice(Class<T> entity, int elapsedMonths, String fieldName) {
        LocalDate month = LocalDate.now().minusMonths(elapsedMonths);
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root<T> root = query.from(entity);

        Expression<Integer> sumExpression = builder.sum(root.get(fieldName));

        Predicate betweenPredicate = builder.between(root.get("regDate"), startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));

        query.where(betweenPredicate);
        query.select(sumExpression);

        return entityManager.createQuery(query).getSingleResult();
    }


    public static <T> Specification<T> getEntityList(Class<T> entity, int elapsedMonths, String fieldName) {
        return (root, query, builder) -> {
            LocalDate month = LocalDate.now().minusMonths(elapsedMonths);
            LocalDate startDate = month.withDayOfMonth(1);
            LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());
            return builder.between(root.get(fieldName), startDate, endDate);
        };
    }
}
