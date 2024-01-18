package com.bit.auction.user.repository;

import com.bit.auction.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    int countByUserId(String userId);
}
