package com.dreamss.dreamjourneyuser.controller;

import com.dreamss.dreamjourneycommon.enums.ResultEnum;
import com.dreamss.dreamjourneycommon.enums.UserStatusEnum;
import com.dreamss.dreamjourneyuser.service.AdminService;
import com.dreamss.dreamjourneyuser.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

/**
 * @author Created by DrEAmSs on 2022-05-30 14:09
 */
@Api(tags = "管理员")
@RequestMapping("admin")
@RestController
public class AdminController {

    @Resource
    private AdminService adminService;

    @ApiOperation("管理员登录")
    @PostMapping("/adminLogin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginVO loginVO) {
        return ResponseEntity.ok(adminService.adminLogin(loginVO));
    }

    @ApiOperation("获取用户列表")
    @GetMapping("/queryUserList")
    public ResponseEntity<?> queryUserList(@RequestParam(required = false) String username,
                                           @RequestParam(required = false) Long mobile,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) UserStatusEnum userStatus,
                                           @RequestParam Integer pageIndex,
                                           @RequestParam Integer pageSize,
                                           ServletRequest servletRequest) {
        return ResponseEntity.ok(adminService.queryUserList(username, mobile, email, userStatus, pageIndex, pageSize,
                servletRequest));
    }

    @ApiOperation("封禁某一用户")
    @GetMapping("/ban/{id}")
    public ResponseEntity<?> banUserById(@PathVariable String id) {
        adminService.banUserById(id);
        return ResponseEntity.ok(ResultEnum.SUCCESS.getLabel());
    }

    @ApiOperation("封禁某一用户")
    @GetMapping("/unban/{id}")
    public ResponseEntity<?> unbanUserById(@PathVariable String id) {
        adminService.unbanUserById(id);
        return ResponseEntity.ok(ResultEnum.SUCCESS.getLabel());
    }
}
