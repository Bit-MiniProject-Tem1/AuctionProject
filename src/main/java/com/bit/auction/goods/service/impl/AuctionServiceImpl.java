package com.bit.auction.goods.service.impl;

import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.AuctionImg;
import com.bit.auction.goods.entity.Category;
import com.bit.auction.goods.repository.AuctionImgRepository;
import com.bit.auction.goods.repository.AuctionRepository;
import com.bit.auction.goods.repository.CategoryRepository;
import com.bit.auction.goods.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;
    private final AuctionImgRepository auctionImgRepository;

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

    @Transactional
    @Override
    public void insertAuction(AuctionDTO auctionDTO, Long categoryId) {
        if (auctionDTO.getTitle().equals("")) {
            throw new RuntimeException("title cannot be null");
        }

        if (auctionDTO.getDescription().equals("")) {
            throw new RuntimeException("description cannot be null");
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        auctionDTO.setRegUserId("kim");
        auctionDTO.setStatus('S');

        Auction auction = auctionDTO.toEntity(category);

        List<AuctionImg> auctionImgList = auctionDTO.getAuctionImgDTOList().stream()
                .map(auctionImgDTO -> auctionImgDTO.toEntity(auction))
                .toList();

        auctionImgList.forEach(auctionImg -> {
            auctionImg.setAuction(auction);
            auction.addAuctionImg(auctionImg);
        });

        auctionRepository.saveAndFlush(auction);
        auctionImgRepository.saveAllAndFlush(auctionImgList);
    }
}
