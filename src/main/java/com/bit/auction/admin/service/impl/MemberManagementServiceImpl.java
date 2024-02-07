package com.bit.auction.admin.service.impl;

import com.bit.auction.admin.repository.BiddingInfoRepository;
import com.bit.auction.admin.repository.InquiryInfoRepository;
import com.bit.auction.admin.repository.MemberRepository;
import com.bit.auction.admin.service.MemberManagementService;
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
    private final MemberRepository memberRepository;
    private final InquiryInfoRepository inquiryRepository;
    private final BiddingInfoRepository biddingRepository;

    @Override
    public UserDTO getMember(Long id) {
        return null;
    }

    @Override
    public Page<UserDTO> getMemberList(Pageable pageable, UserDTO userDTO) {
        Page<User> userPageList = memberRepository.findAll(pageable);
        String condition = userDTO.getSearchCondition();
        String keyword = userDTO.getSearchKeyword();

        if(keyword != null && !keyword.equals("")){
            if(condition.equals("전체")) {
                userPageList = memberRepository
                        .findByUserNameContainingOrUserIdContainingOrUserTelContainingOrUserEmailContaining(pageable, keyword, keyword, keyword, keyword);
            } else if(condition.equals("이름")) {
                userPageList = memberRepository.findByUserNameContaining(
                        pageable, keyword);
            } else if(condition.equals("아이디")) {
                userPageList = memberRepository.findByUserIdContaining(
                        pageable, keyword);
            } else if(condition.equals("연락처")) {
                userPageList = memberRepository.findByUserTelContaining(
                        pageable, keyword);
            } else if(condition.equals("이메일")) {
                userPageList = memberRepository.findByUserEmailContaining(
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
        return memberRepository.findById(id).get().toDTO();
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