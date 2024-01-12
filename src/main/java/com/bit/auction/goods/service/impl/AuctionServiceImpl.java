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

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<AuctionDTO> getAuctionList(Pageable pageable, Long categoryId, String filter) {
        Page<Auction> auctionPageList = auctionRepository.findAll(pageable);

        Long[] subCategoryIdList = categoryRepository.findSubCategoryIdList(categoryId);

        if (filter.equals("top")) {
            auctionPageList = auctionRepository.findByIdIn(pageable, subCategoryIdList);
        } else if (filter.equals("sub")) {
            auctionPageList = auctionRepository.findByCategoryId(pageable, categoryId);
        } else if (filter.equals("etc")) {
            auctionPageList = auctionRepository.findByCategoryId(pageable, categoryId);
        }

        Page<AuctionDTO> auctionDTOPageList = auctionPageList.map(auction -> auction.toDTO());
        return auctionDTOPageList;
    }
}
