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

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;
    private List<Character> statusList = new ArrayList<>(List.of('S'));

    @Override
    public Page<AuctionDTO> getAuctionList(Pageable pageable, Long categoryId, String filter, List<String> target) {
        List<Long> categoryIdList = new ArrayList<>();

        if (filter.equals("top")) {
            categoryIdList = categoryRepository.findSubCategoryIdList(categoryId);
            categoryIdList.add(categoryId);
        } else if (filter.equals("all")) {
            categoryId = 0L;
        }

        Page<Auction> auctionPageList = auctionRepository.searchAll(pageable, categoryId, categoryIdList, target, statusList);

        Page<AuctionDTO> auctionDTOPageList = auctionPageList.map(auction -> auction.toDTO());
        return auctionDTOPageList;
    }

    @Override
    public void addSearchStatus(char status) {
        this.statusList.add(status);
    }
}
