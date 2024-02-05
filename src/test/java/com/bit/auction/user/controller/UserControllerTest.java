package com.bit.auction.user.controller;

import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class UserControllerTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @DisplayName("admin 권한 주기")
    @Test
    void join() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("admin");
        userDTO.setUserPw(passwordEncoder.encode("123"));
        userDTO.setUserName("관리자");
        userDTO.setUserBirth("999999");
        userDTO.setUserTel("01099999999");
        userDTO.setUserAddress("수원시");
        userDTO.setUserEmail("bit@camp.com");
        userDTO.setUserRegdate(LocalDateTime.now());
        userDTO.setRole("ROLE_ADMIN");

        userService.join(userDTO);


    }
}