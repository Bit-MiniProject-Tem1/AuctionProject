package com.bit.auction.user.repository;

import com.bit.auction.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    int countByUserId(String userId);

    Optional<User> findByUserIdAndUserPw(String userId, String userPw);

    Optional<User> findByUserId(String username);

    Optional<User> findByUserNameAndUserTel(String userName, String userTel);

    Optional<User> findByUserIdAndUserNameAndUserTel(String userId, String userName, String userTel);

    Optional<User> findUserByUserId(String userId);

}
