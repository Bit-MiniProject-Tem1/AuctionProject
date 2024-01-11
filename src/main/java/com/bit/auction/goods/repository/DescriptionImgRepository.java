package com.bit.auction.goods.repository;

import com.bit.auction.goods.entity.DescriptionImg;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface DescriptionImgRepository extends JpaRepository<DescriptionImg, Long> {
}
