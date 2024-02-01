package com.bit.auction.user.service.impl;

import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.UserRepository;
import com.bit.auction.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

//    private String generateNewPassword() {
//        // 새로운 비밀번호를 생성하는 로직
//        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
//        String numberChars = "0123456789";
//        String specialChars = "!@#$%^&*()-_+=<>?/{}";
//
//        String allChars = upperChars + lowerChars + numberChars + specialChars;
//
//        int passwordLength = 12;
//        StringBuilder password = new StringBuilder();
//
//        for (int i = 0; i < passwordLength; i++) {
//            int index = (int) (allChars.length() * Math.random());
//            password.append(allChars.charAt(index));
//        }
//        return password.toString();
//    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public int idCheck(String userId) {
        return userRepository.countByUserId(userId);
    }

    @Override
    public void join(UserDTO userDTO) {
        userDTO.setUserRegdate(LocalDateTime.now());

        User user = userDTO.toEntity();

        userRepository.save(user);

    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUserIdAndUserPw(userDTO.getUserId(), userDTO.getUserPw());

        if (userOptional.isEmpty()) {
            return null;
        }

        return userOptional.get().toDTO();
    }

    @Override
    public Optional<User> findId(String userName, String userTel) {
        return userRepository.findByUserNameAndUserTel(userName, userTel);
    }

    @Override
    public Optional<User> findPw(String userId, String userName, String userTel) {
//        Optional<User> userOptional = userRepository.findByUserIdAndUserNameAndUserTel(userId,userName,userTel);
        return userRepository.findByUserIdAndUserNameAndUserTel(userId, userName, userTel);
    }

    @Override
    public void modify(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).get();

        if (!passwordEncoder.matches(userDTO.getCurUserPw(), user.getUserPw())){
            throw new RuntimeException("wrong password");
        }

        userDTO.setRole(user.getRole());

        if (userDTO.getUserPw() == null || userDTO.getUserPw().equals("")) {
            userDTO.setUserPw(passwordEncoder.encode(userDTO.getCurUserPw()));
        } else {
            userDTO.setUserPw(passwordEncoder.encode(userDTO.getUserPw()));
        }

        userRepository.save(userDTO.toEntity());
    }

    @Override
    public UserDTO findUser(String userId) {
        Optional<User> optionalUser = userRepository.findUserByUserId(userId);
        return optionalUser.map(User::toDTO).orElse(null);
    }

}
