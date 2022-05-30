package com.dreamss.dreamjourneyuser.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dreamss.dreamjourneycommon.enums.UserStatusEnum;
import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserVO;

import javax.servlet.ServletRequest;

/**
 * @author Created by DrEAmSs on 2022-05-30 14:10
 */
public interface AdminService {

    /**
     * 管理员登录
     */
    String adminLogin(LoginVO loginVO);

    /**
     * 获取用户列表
     */
    Page<UserVO> queryUserList(String username, Long mobile, String email, UserStatusEnum userStatus, Integer pageIndex,
                               Integer pageSize, ServletRequest servletRequest);

    /**
     * 冻结用户
     */
    void banUserById(String id);

    /**
     * 解冻
     */
    void unbanUserById(String id);
}
