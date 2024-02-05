package com.bit.auction.user.dto;


import com.bit.auction.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@ToString
public class UserDTO {
    private long id;
    private String userId;
    private String userPw;
    private String userName;
    private String userBirth;
    private String userTel;
    private String userAddress;
    private String userEmail;
    private LocalDateTime userRegdate;
    private String role;
    private String curUserPw;
    private String searchCondition;
    private String searchKeyword;



public User toEntity() {
    return User.builder()
            .id(this.id)
            .userId(this.userId)
            .userPw(this.userPw)
            .userName(this.userName)
            .userBirth(this.userBirth)
            .userTel(this.userTel)
            .userAddress(this.userAddress)
            .userEmail(this.userEmail)
            .userRegdate(LocalDateTime.now())
            .role(this.role)
            .build();
    }
}
