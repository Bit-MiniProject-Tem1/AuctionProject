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

        log.info("현재월 : {}월", currentDate);
        log.info("###################");
        for(int i = 0; i < monthsCnt; i++) {
            monthList[i] =  currentDate.minusMonths(i).format(formatter);
            log.info(monthList[i]);
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

        log.info("### biddingCountList = {}", biddingCountList);

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

        log.info("### auctionCountList = {}", auctionCountList);

        return auctionCountList;
    }

    @Override
    public int[] getTotalPriceList() {
        int[] totalPriceList = new int[monthsCnt];
        String fieldName = "currentBiddingPrice";

        for(int i = 0; i < monthsCnt; i++) {
            totalPriceList[i] = getTotalPrice(Auction.class, i, fieldName);
        }

        log.info("### totalPriceList = {}", totalPriceList);

        return totalPriceList;
    }

    public <T> int getTotalPrice(Class<T> entity, int elapsedMonths, String fieldName) {
        LocalDate month = LocalDate.now().minusMonths(elapsedMonths);
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());

        log.info("1");
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        log.info("2");
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        log.info("3");
        Root<T> root = query.from(entity);
        log.info("4");

        // 기간에 해당하는 데이터들의 price 합계를 구하는 표현식 생성
        Expression<Integer> sumExpression = builder.sum(root.get(fieldName));
        log.info("5");

        // 기간에 해당하는 데이터들을 선택하기 위한 조건 추가
        Predicate betweenPredicate = builder.between(root.get("regDate"), startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        log.info("6");

        // 쿼리에 조건 추가
        query.where(betweenPredicate);
        log.info("7");
        query.select(sumExpression);
        log.info("8");

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
