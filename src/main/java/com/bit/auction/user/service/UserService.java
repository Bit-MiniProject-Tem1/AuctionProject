package com.bit.auction.user.service;

import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.User;

import java.util.Optional;

public interface UserService {
    int idCheck(String userId);

    void join(UserDTO userDTO);

    UserDTO login(UserDTO userDTO);

    Optional<User> findId(String userName, String userTel);

    Optional<User> findPw(String userId, String userName, String userTel);
}
