package com.dreamss.dreamjourneyuser.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserRegisterVO;
import com.dreamss.dreamjourneyuser.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 上传用户头像
     */
    void uploadAvatar(MultipartFile multipartFile, ServletRequest servletRequest);

    /**
     * 获取用户头像
     */
    void getAvatar(String avatar, HttpServletResponse response);

    /**
     * 获取用户列表
     */
    Page<UserVO> queryUserList(String username, Long mobile, String email, Integer pageIndex, Integer pageSize,
                               ServletRequest servletRequest);
}
