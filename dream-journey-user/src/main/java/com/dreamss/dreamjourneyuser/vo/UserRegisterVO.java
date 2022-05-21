package com.dreamss.dreamjourneyuser.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Created by DrEAmSs on 2022-05-20 16:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class UserRegisterVO extends UserVO{

    @ApiModelProperty("密码")
    private String password;
}
