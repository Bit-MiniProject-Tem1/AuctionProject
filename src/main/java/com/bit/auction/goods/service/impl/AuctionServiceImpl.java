package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.repository.AuctionRepository;
import com.bit.auction.goods.repository.CategoryRepository;
import com.bit.auction.goods.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<AuctionDTO> getAuctionList(Pageable pageable, Long categoryId, String filter, List<String> target, List<Character> status) {
        List<Long> categoryIdList = new ArrayList<>();

        if (filter.equals("top")) {
            categoryIdList = categoryRepository.findSubCategoryIdList(categoryId);
            categoryIdList.add(categoryId);
        } else if (filter.equals("all")) {
            categoryId = 0L;
        }
        List<Character> statusList = new ArrayList<>();
        if (status != null || !status.isEmpty()) {
            statusList.add('S');
            statusList.addAll(status);
        }

        Page<Auction> auctionPageList = auctionRepository.searchAll(pageable, categoryId, categoryIdList, target, statusList);

        Page<AuctionDTO> auctionDTOPageList = auctionPageList.map(auction -> auction.toDTO());
        return auctionDTOPageList;
    }

    @Override
    public void insertAuction(AuctionDTO auctionDTO) {
    }

    @Override
    public List<AuctionDTO> searchAuctions(String searchQuery, List<Character> status) {
        List<Character> statusList = new ArrayList<>();
        if (status != null || !status.isEmpty()) {
            statusList.add('S');
            statusList.addAll(status);
        }

        return auctionRepository
                .findByAuctionNameContaining(searchQuery, statusList)
                .stream()
                .map(Auction::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionDTO> findByForRecentList() {

        List<Auction> recentAuctions = auctionRepository.findByforResent();
        return recentAuctions.stream()
                .map(Auction::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<AuctionDTO> findByForFinalList() {
        List<Auction> finalAuctions = auctionRepository.findByforFinal();
        return finalAuctions.stream()
                .map(Auction::toDTO)
                .collect(Collectors.toList());
    }


}
