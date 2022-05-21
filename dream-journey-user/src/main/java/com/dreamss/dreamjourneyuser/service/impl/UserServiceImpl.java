package com.dreamss.dreamjourneyuser.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.dreamss.dreamjourneycommon.enums.ResponseCodeEnum;
import com.dreamss.dreamjourneycommon.enums.ResultEnum;
import com.dreamss.dreamjourneycommon.enums.UserTypeEnum;
import com.dreamss.dreamjourneycommon.excepitons.DreamException;
import com.dreamss.dreamjourneycommon.utils.Constant;
import com.dreamss.dreamjourneycommon.utils.JwtUtils;
import com.dreamss.dreamjourneyuser.dao.UserDao;
import com.dreamss.dreamjourneyuser.entities.User;
import com.dreamss.dreamjourneyuser.service.UserService;
import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserRegisterVO;
import com.dreamss.dreamjourneyuser.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Objects;
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
        }
        if (userOptional.get().getPassword().equals(loginVO.getPassword())) {
            return JwtUtils.createToken(userOptional.get().getId(), userOptional.get().getNickname()
                    , userOptional.get().getUsername());
        } else {
            throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.LOGIN_FAIL.getLabel());
        }
    }

    @Override
    public String adminLogin(LoginVO loginVO) {
        Optional<User> userOptional = userDao.lambdaQuery().eq(User::getUsername, loginVO.getUsername()).oneOpt();
        if (userOptional.isEmpty()) {
            throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.USERNAME_NOT_EXISTS.getLabel());
        }
        if (!Objects.equals(userOptional.get().getUserType(), UserTypeEnum.ADMIN)) {
            throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.ACCESS_DENIED.getLabel());
        }
        if (userOptional.get().getPassword().equals(loginVO.getPassword())) {
            return JwtUtils.createToken(userOptional.get().getId(), userOptional.get().getNickname()
                    , userOptional.get().getUsername());
        } else {
            throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.LOGIN_FAIL.getLabel());
        }
    }

    @Override
    public void registerOrUpdate(UserRegisterVO userVO) {
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

    @Override
    public UserVO currentUserInfo(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        UserVO userVO = new UserVO();
        String userId = JwtUtils
                .getAudience(Objects.requireNonNull(httpServletRequest.getHeader(Constant.TOKEN)));
        Optional<User> user = userDao.lambdaQuery().eq(User::getId, userId).oneOpt();
        user.ifPresent(value -> BeanUtils.copyProperties(value, userVO));
        return userVO;
    }
}
