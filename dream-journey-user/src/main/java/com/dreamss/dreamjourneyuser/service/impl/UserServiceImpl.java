package com.dreamss.dreamjourneyuser.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.dreamss.dreamjourneycommon.enums.ResponseCodeEnum;
import com.dreamss.dreamjourneycommon.enums.ResultEnum;
import com.dreamss.dreamjourneycommon.excepitons.DreamException;
import com.dreamss.dreamjourneycommon.utils.JwtUtils;
import com.dreamss.dreamjourneyuser.dao.UserDao;
import com.dreamss.dreamjourneyuser.entities.User;
import com.dreamss.dreamjourneyuser.service.UserService;
import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * @author Created by DrEAmSs on 2022-05-20 14:56
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public String login(LoginVO loginVO) {
        Optional<User> userOptional = userDao.lambdaQuery().eq(User::getUsername, loginVO.getUsername()).oneOpt();
        if (userOptional.isEmpty()) {
            throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.USERNAME_NOT_EXISTS.getLabel());
        } else {
            if (userOptional.get().getPassword().equals(loginVO.getPassword())) {
                return JwtUtils.createToken(userOptional.get().getId(), userOptional.get().getNickname()
                        , userOptional.get().getUsername());
            } else {
                throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.LOGIN_FAIL.getLabel());
            }
        }
    }

    @Override
    public void registerOrUpdate(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        if (StringUtils.isNotBlank(user.getId())) {
            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            Optional<User> userOptional = userDao.lambdaQuery().eq(User::getUsername, userVO.getUsername()).oneOpt();
            if (userOptional.isPresent()) {
                throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.USERNAME_EXISTS.getLabel());
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            user.setCreateTime(timestamp);
            user.setUpdateTime(timestamp);
        }
        userDao.saveOrUpdate(user);
    }
}
