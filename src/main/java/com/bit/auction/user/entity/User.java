package com.bit.auction.user.entity;


import com.bit.auction.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(unique = true, name = "user_id")
    private String userId;
    @Column(nullable = false, name = "user_pw")
    private String userPw;
    @Column(nullable = false, name = "user_name")
    private String userName;
    @Column(nullable = false, name = "user_birth")
    private String userBirth;
    @Column(nullable = false, name = "user_tel")
    private String userTel;
    @Column(nullable = false, name = "user_address")
    private String userAddress;
    @Column(nullable = false, name = "user_email")
    private String userEmail;
    @Column(name = "user_regdate")
    private LocalDateTime userRegdate;
    @Column(name = "role")
    private String role;

    @Transient
    private String findId;
    @Transient
    private String searchCondition;
    @Transient
    private String searchKeyword;

    public UserDTO toDTO() {
        return UserDTO.builder()
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



