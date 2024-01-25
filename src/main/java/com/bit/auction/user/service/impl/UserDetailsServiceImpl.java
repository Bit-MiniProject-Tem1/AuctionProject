package com.bit.auction.user.service.impl;

import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserId(username);

        if (userOptional.isEmpty()) {
            return null;
        }
        return CustomUserDetails.builder()
                .user(userOptional.get())
                .build();
    }
}
