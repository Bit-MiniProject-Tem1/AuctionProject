package com.bit.auction.user.dto;


import com.bit.auction.user.entity.User;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class UserDTO {
    private long id;
    private String userId;
    private String userPw;
    private String userName;
    private String userBirth;
    private String userTel;
    private String userAddress;
    private String userEmail;
    private String userRegdate;

}

//public User toEntity() {
//    return User.builder()
//            .id(this.id)
//            .userId(this.userId)
//            .userName(this.userName)
//            .userBirth(this.userBirth)
//            .userTel(this.userTel)
//            .userAddress(this.userAddress)
//            .userEmail(this.userEmail)
//            .build();
//}
