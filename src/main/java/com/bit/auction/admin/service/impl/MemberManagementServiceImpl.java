package com.bit.auction.admin.service.impl;

import com.bit.auction.admin.repository.MemberInfo_BiddingRepository;
import com.bit.auction.admin.repository.MemberInfo_InquiryRepository;
import com.bit.auction.admin.repository.MemberManagementRepository;
import com.bit.auction.admin.service.MemberManagementService;
import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.dto.BiddingDTO;
import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberManagementServiceImpl implements MemberManagementService {

    private final EntityManager entityManager;
    private final MemberManagementRepository memberManagementRepository;
    private final MemberInfo_InquiryRepository inquiryRepository;
    private final MemberInfo_BiddingRepository biddingRepository;

    @Override
    public UserDTO getMember(Long id) {
        return null;
    }

    @Override
    public Page<UserDTO> getMemberList(Pageable pageable, UserDTO userDTO) {
        Page<User> userPageList = memberManagementRepository.findAll(pageable);
        String condition = userDTO.getSearchCondition();
        String keyword = userDTO.getSearchKeyword();

        if(keyword != null && !keyword.equals("")){
            if(condition.equals("전체")) {
                userPageList = memberManagementRepository
                        .findByUserNameContainingOrUserIdContainingOrUserTelContainingOrUserEmailContaining(pageable, keyword, keyword, keyword, keyword);
            } else if(condition.equals("이름")) {
                userPageList = memberManagementRepository.findByUserNameContaining(
                        pageable, keyword);
            } else if(condition.equals("아이디")) {
                userPageList = memberManagementRepository.findByUserIdContaining(
                        pageable, keyword);
            } else if(condition.equals("연락처")) {
                userPageList = memberManagementRepository.findByUserTelContaining(
                        pageable, keyword);
            } else if(condition.equals("이메일")) {
                userPageList = memberManagementRepository.findByUserEmailContaining(
                        pageable, keyword);
            }

        }

        return userPageList.map(user -> user.toDTO());
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public UserDTO findById(Long id) {
        return memberManagementRepository.findById(id).get().toDTO();
    }

    @Override
    public List<InquiryDTO> findByInquiryWriter(String userId) {
        return inquiryRepository.findByInquiryWriter(userId)
                .stream().map(inquiry -> inquiry.toDTO()).toList();
    }

    @Override
    public List<BiddingDTO> findByUserId(String userId) {
        return biddingRepository.findByUserId(userId)
                .stream().map(bidding -> bidding.toDTO()).toList();
    }




}