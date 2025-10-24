package com.zane.linkee_platform.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String userLoginId;
    private String userPw;
    private String userName;
    private String userNickname;

    @Override
    public String toString() {
        return "UserDTO{" +
                "userLoginId='" + userLoginId + '\'' +
                ", userPw='" + userPw + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                '}';
    }
}
