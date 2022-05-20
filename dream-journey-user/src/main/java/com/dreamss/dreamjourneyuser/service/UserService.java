package com.dreamss.dreamjourneyuser.service;

import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserVO;

/**
 * @author Created by DrEAmSs on 2022-05-20 14:56
 */
public interface UserService {

    /**
     * 用户登录
     */
    String login(LoginVO loginVO);

    /**
     * 用户注册或更新信息
     */
    void registerOrUpdate(UserVO userVO);
}
