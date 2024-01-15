package com.bit.auction.goods.service;

import com.bit.auction.goods.dto.AuctionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuctionService {
    Page<AuctionDTO> getAuctionList(Pageable pageable, Long categoryId, String filter, List<String> target);

    void addSearchStatus(char status);
}
