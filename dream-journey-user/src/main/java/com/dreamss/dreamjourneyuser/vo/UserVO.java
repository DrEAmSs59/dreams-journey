package com.dreamss.dreamjourneyuser.vo;

import com.dreamss.dreamjourneycommon.enums.UserGenderEnum;
import com.dreamss.dreamjourneycommon.enums.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserVO {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

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
    private List<String> city;

    @ApiModelProperty("工作")
    private String job;

    @ApiModelProperty("个性签名")
    private String sign;

    @ApiModelProperty("用户类型")
    private UserTypeEnum userType;

    @ApiModelProperty("账号来源")
    private Integer sourceType;
}
