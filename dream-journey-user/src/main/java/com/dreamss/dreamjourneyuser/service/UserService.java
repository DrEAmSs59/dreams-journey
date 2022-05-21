package com.dreamss.dreamjourneyuser.service;

import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserRegisterVO;
import com.dreamss.dreamjourneyuser.vo.UserVO;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.ServletRequest;

/**
 * @author Created by DrEAmSs on 2022-05-20 14:56
 */
public interface UserService {

    /**
     * 用户登录
     */
    String login(LoginVO loginVO);

    /**
     * 管理员登录
     */
    String adminLogin(LoginVO loginVO);

    /**
     * 用户注册或更新信息
     */
    void registerOrUpdate(UserRegisterVO userVO);

    /**
     * 获取当前用户信息
     */
    UserVO currentUserInfo(ServletRequest servletRequest);
}
