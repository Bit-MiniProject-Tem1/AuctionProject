package com.bit.auction.user.entity;


import com.bit.auction.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_INFO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(unique = true)
    private String userId;
    private String userPw;
    private String userName;
    private String userBirth;
    private String userTel;
    private String userAddress;
    private String userEmail;
    private String userRegdate;
}
//public UserDTO toDTO() {
//    return UserDTO.builder()
//            .id(this.id)
//            .userId(this.userId)
//            .userName(this.userName)
//            .userBirth(this.userBirth)
//            .userTel(this.userTel)
//            .userAddress(this.userAddress)
//            .userEmail(this.userEmail)
//            .build();
//}



