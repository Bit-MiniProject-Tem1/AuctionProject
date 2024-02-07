package com.bit.auction.admin.service;

public interface StatisticalDataService {
    String[] getStatisticalPeriod();

    int[] getBiddingCountList();

    int[] getAuctionCountList();

    int[] getTotalPriceList();
}
