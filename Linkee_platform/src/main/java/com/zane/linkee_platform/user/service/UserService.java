package com.zane.linkee_platform.user.service;

import com.zane.linkee_platform.user.dto.UserDTO;
import com.zane.linkee_platform.user.entity.User;
import com.zane.linkee_platform.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동을 만들어준다.
public class UserService {
    private final UserRepository userRepository;

    public void signup(UserDTO userDTO){
        User user = new User();
        user.setUserLoginId(userDTO.getUserLoginId());
        user.setUserPw(userDTO.getUserPw());
        user.setUserNickname(userDTO.getUserNickname());
        userRepository.save(user); // DB에 저장
    }
}
