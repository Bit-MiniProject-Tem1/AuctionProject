package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.repository.AuctionRepository;
import com.bit.auction.goods.service.SchedulerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final AuctionRepository auctionRepository;

    @Override
    @Transactional
    @Scheduled(cron = "0 0 12 * * *")
    public void setStateChangeFromEndDate() {
        auctionRepository.updateStatusByEndDate(LocalDateTime.now());
    }
}
