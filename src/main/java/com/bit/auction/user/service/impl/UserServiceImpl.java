package com.bit.auction.user.service.impl;

import com.bit.auction.user.repository.UserRepository;
import com.bit.auction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public int idCheck(String userId) {
        return userRepository.countByUserId(userId);
    }
}
