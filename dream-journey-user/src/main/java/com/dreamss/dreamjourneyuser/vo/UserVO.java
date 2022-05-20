package com.dreamss.dreamjourneyuser.vo;

import com.dreamss.dreamjourneycommon.enums.UserGenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Created by DrEAmSs on 2022-05-20 16:59
 */
@Data
@ApiModel
public class UserVO {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("电话号码")
    private Long mobile;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别")
    private UserGenderEnum gender;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("所在城市")
    private String city;

    @ApiModelProperty("工作")
    private String job;

    @ApiModelProperty("个性签名")
    private String sign;

    @ApiModelProperty("账号来源")
    private Integer sourceType;
}
