package com.dreamss.dreamjourneyuser.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dreamss.dreamjourneycommon.enums.UserGenderEnum;
import com.dreamss.dreamjourneycommon.enums.UserStatusEnum;
import com.dreamss.dreamjourneycommon.enums.UserTypeEnum;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Created by DrEAmSs on 2022-05-20 13:53
 */
@Data
@TableName("t_user")
public class User {

    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话号码
     */
    private Long mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private UserGenderEnum gender;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 工作
     */
    private String job;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 账号来源
     */
    private Integer sourceType;

    /**
     * 账号类型
     */
    private UserTypeEnum userType;

    /**
     * 用户积分
     */
    private Integer integration;

    /**
     * 用户成长值
     */
    private Integer growth;

    /**
     * 用户状态
     */
    private UserStatusEnum status;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
