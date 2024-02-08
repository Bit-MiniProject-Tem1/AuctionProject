package com.bit.auction.admin.service;

import java.util.List;

public interface StatisticalDataService {
    List<String> getStatisticalPeriod();

    List<Integer> getBiddingCountList();

    List<Integer> getAuctionCountList();

    List<Integer> getTotalPriceList();
}
