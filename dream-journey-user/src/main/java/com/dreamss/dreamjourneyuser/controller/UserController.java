package com.dreamss.dreamjourneyuser.controller;

import com.dreamss.dreamjourneycommon.enums.ResultEnum;
import com.dreamss.dreamjourneyuser.service.UserService;
import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserRegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

/**
 * @author Created by DrEAmSs on 2022-05-20 13:19
 */
@Api(tags = "用户")
@RequestMapping("user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVO loginVO) {
        return ResponseEntity.ok(userService.login(loginVO));
    }

    @ApiOperation("管理员登录")
    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginVO loginVO) {
        return ResponseEntity.ok(userService.adminLogin(loginVO));
    }

    @ApiOperation("用户注册或更新信息")
    @PostMapping("/registerOrUpdate")
    public ResponseEntity<?> registerOrUpdate(@RequestBody UserRegisterVO userVO) {
        userService.registerOrUpdate(userVO);
        return ResponseEntity.ok(ResultEnum.SUCCESS.getLabel());
    }

    @ApiOperation("获取当前用户登录信息")
    @GetMapping("/currentUserInfo")
    public ResponseEntity<?> currentUserInfo(ServletRequest servletRequest) {
        return ResponseEntity.ok(userService.currentUserInfo(servletRequest));
    }
}
