package com.bit.auction.admin.repository;

import com.bit.auction.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<User, Long> {
    Page<User> findByUserNameContainingOrUserIdContainingOrUserTelContainingOrUserEmailContaining(Pageable pageable, String userName, String userId, String userTel, String userEmail);

    Page<User> findByUserNameContaining(Pageable pageable, String userName);

    Page<User> findByUserIdContaining(Pageable pageable, String userId);

    Page<User> findByUserTelContaining(Pageable pageable, String userTel);

    Page<User> findByUserEmailContaining(Pageable pageable, String userEmail);

}
