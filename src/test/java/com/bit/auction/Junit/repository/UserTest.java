package com.bit.auction.Junit.repository;

import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            User user = User.builder()
                    .userId("testId" + i)
                    .userPw("testPw" + i)
                    .userEmail("testEmail" + i + "@test.com")
                    .userName("testName" + i)
                    .userBirth("testBirth" + i)
                    .userAddress("testAddress" + i)
                    .userTel("testTel" + i)
                    //.userRegdate("testReg" + i)
                    .build();

            userRepository.save(user);

        });
    }

}
