package com.jyang.boardserver.service.impl;

import static com.jyang.boardserver.util.SHA256Util.encryptSHA256;

import com.jyang.boardserver.dto.UserDTO;
import com.jyang.boardserver.exception.DuplicateIdException;
import com.jyang.boardserver.mapper.UserProfileMapper;
import com.jyang.boardserver.service.UserService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public void register(UserDTO userProfile) {
        System.out.println(userProfile);
        boolean isDuplicatedId = isDuplicatedId(userProfile.getUserId());

        if (isDuplicatedId) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }

        userProfile.setCreateTime(new Date());
        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));
        int insertCount = userProfileMapper.register(userProfile);

        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", userProfile);
            throw new RuntimeException("insertUser ERROR! 회원가입 메서드를 확인해 주세요\n" + "Params : " + userProfile);
        }
    }

    @Override
    public UserDTO login(String userId, String password) {
        String cryptoPassword = encryptSHA256(password);
        return userProfileMapper.findByIdAndPassword(userId, cryptoPassword);
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        System.out.println(userId);
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = encryptSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(encryptSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePassword ERROR! {}", id);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

    }

    @Override
    public void deleteId(String userId, String password) {
        String cryptoPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(userId, cryptoPassword);

        if (memberInfo != null) {
            int deleteCount = userProfileMapper.deleteUserProfile(userId);
        } else {
            log.error("deleteId ERROR! {}", userId);
            throw new RuntimeException("유저 삭제처리가 실패했습니다.");
        }
    }
}
