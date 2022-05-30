package com.dreamss.dreamjourneyuser.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dreamss.dreamjourneycommon.enums.ResponseCodeEnum;
import com.dreamss.dreamjourneycommon.enums.ResultEnum;
import com.dreamss.dreamjourneycommon.enums.UserStatusEnum;
import com.dreamss.dreamjourneycommon.enums.UserTypeEnum;
import com.dreamss.dreamjourneycommon.excepitons.DreamException;
import com.dreamss.dreamjourneycommon.utils.Constant;
import com.dreamss.dreamjourneycommon.utils.JwtUtils;
import com.dreamss.dreamjourneyuser.dao.UserDao;
import com.dreamss.dreamjourneyuser.entities.User;
import com.dreamss.dreamjourneyuser.service.AdminService;
import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Created by DrEAmSs on 2022-05-20 14:56
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Resource
    private UserDao userDao;

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
    public Page<UserVO> queryUserList(String username, Long mobile, String email, UserStatusEnum userStatus,
                                      Integer pageIndex, Integer pageSize, ServletRequest servletRequest) {
        Page<User> pageParam = new Page<>(pageIndex, pageSize);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Page<User> userPage = new LambdaQueryChainWrapper<>(userDao.getBaseMapper())
                .like(StringUtils.isNotBlank(username), User::getUsername, username)
                .like(StringUtils.isNotBlank(email), User::getEmail, email)
                .like(Objects.nonNull(mobile), User::getMobile, mobile)
                .eq(Objects.nonNull(userStatus), User::getUserStatus, userStatus)
                .ne(User::getId, JwtUtils
                        .getAudience(Objects.requireNonNull(httpServletRequest.getHeader(Constant.TOKEN))))
                .orderBy(true, false, User::getUpdateTime)
                .page(pageParam);
        Long total = userDao.lambdaQuery().like(StringUtils.isNotBlank(username), User::getUsername, username)
                .like(StringUtils.isNotBlank(email), User::getEmail, email)
                .like(Objects.nonNull(mobile), User::getMobile, mobile)
                .eq(Objects.nonNull(userStatus), User::getUserStatus, userStatus)
                .ne(User::getId, JwtUtils
                        .getAudience(Objects.requireNonNull(httpServletRequest.getHeader(Constant.TOKEN))))
                .count();
        Page<UserVO> userVOPage = new Page<>();
        BeanUtils.copyProperties(userPage, userVOPage);
        List<UserVO> userVOS = Lists.newArrayList();
        userPage.getRecords().stream().skip((long) (pageIndex - 1) * pageSize).limit(pageSize).forEach(temp -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(temp, userVO);
            userVOS.add(userVO);
        });
        userVOPage.setRecords(userVOS);
        userVOPage.setTotal(total);
        return userVOPage;
    }

    @Override
    public void banUserById(String id) {
        userDao.lambdaUpdate().set(User::getUserStatus, UserStatusEnum.LOCKED).eq(User::getId, id).update();
    }

    @Override
    public void unbanUserById(String id) {
        userDao.lambdaUpdate().set(User::getUserStatus, UserStatusEnum.UNLOCKED).eq(User::getId, id).update();
    }
}
