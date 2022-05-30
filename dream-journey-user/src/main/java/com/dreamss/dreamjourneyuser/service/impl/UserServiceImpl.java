package com.dreamss.dreamjourneyuser.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dreamss.dreamjourneycommon.enums.ResponseCodeEnum;
import com.dreamss.dreamjourneycommon.enums.ResultEnum;
import com.dreamss.dreamjourneycommon.enums.UserStatusEnum;
import com.dreamss.dreamjourneycommon.enums.UserTypeEnum;
import com.dreamss.dreamjourneycommon.excepitons.DreamException;
import com.dreamss.dreamjourneycommon.utils.Constant;
import com.dreamss.dreamjourneycommon.utils.FileUtils;
import com.dreamss.dreamjourneycommon.utils.JwtUtils;
import com.dreamss.dreamjourneyuser.dao.UserDao;
import com.dreamss.dreamjourneyuser.entities.User;
import com.dreamss.dreamjourneyuser.service.UserService;
import com.dreamss.dreamjourneyuser.vo.LoginVO;
import com.dreamss.dreamjourneyuser.vo.UserRegisterVO;
import com.dreamss.dreamjourneyuser.vo.UserVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Created by DrEAmSs on 2022-05-20 14:56
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Value("${dream-journey.file-path}")
    private String filePath;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String login(LoginVO loginVO) {
        Optional<User> userOptional = userDao.lambdaQuery().eq(User::getUsername, loginVO.getUsername()).oneOpt();
        if (userOptional.isEmpty()) {
            throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.USERNAME_NOT_EXISTS.getLabel());
        }
        if (userOptional.get().getPassword().equals(loginVO.getPassword())) {
            return JwtUtils.createToken(userOptional.get().getId(), userOptional.get().getNickname()
                    , userOptional.get().getUsername());
        } else {
            throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.LOGIN_FAIL.getLabel());
        }
    }

    @Override
    public void registerOrUpdate(UserRegisterVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        try {
            user.setCity(objectMapper.writeValueAsString(userVO.getCity()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(user.getId())) {
            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            Optional<User> userOptional = userDao.lambdaQuery().eq(User::getUsername, userVO.getUsername()).oneOpt();
            if (userOptional.isPresent()) {
                throw new DreamException(ResponseCodeEnum.FAIL.getValue(), ResultEnum.USERNAME_EXISTS.getLabel());
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            user.setCreateTime(timestamp);
            user.setUpdateTime(timestamp);
        }
        userDao.saveOrUpdate(user);
    }

    @Override
    public UserVO currentUserInfo(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        UserVO userVO = new UserVO();
        String userId = JwtUtils
                .getAudience(Objects.requireNonNull(httpServletRequest.getHeader(Constant.TOKEN)));
        Optional<User> user = userDao.lambdaQuery().eq(User::getId, userId).oneOpt();
        user.ifPresent(value -> {
            BeanUtils.copyProperties(value, userVO);
            try {
                userVO.setCity(objectMapper.readValue(value.getCity(), new TypeReference<>() {
                }));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return userVO;
    }

    @Override
    public void uploadAvatar(MultipartFile multipartFile, ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String userId = JwtUtils
                .getAudience(Objects.requireNonNull(httpServletRequest.getHeader(Constant.TOKEN)));
        try {
            String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
            FileUtils.uploadFile(multipartFile.getInputStream(), filePath + File.separator + md5Hex
                    + File.separator + multipartFile.getOriginalFilename());
            userDao.lambdaUpdate().eq(User::getId, userId).set(User::getAvatar, md5Hex).update();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAvatar(String avatar, HttpServletResponse response) {
        String fileName = null;
        File parentPath = new File(filePath + File.separator + avatar);
        if (parentPath.isDirectory()) {
            fileName = Objects.requireNonNull(parentPath.listFiles())[0].getName();
        }
        if (StringUtils.isNotBlank(fileName)) {
            try {
                String contentType = "application/octet-stream";
                if (fileName.toLowerCase().endsWith("jpg")) {
                    contentType = "image/jpeg";
                } else if (fileName.toLowerCase().endsWith("png")) {
                    contentType = "image/png";
                }
                response.setContentType(contentType);
                response.addHeader("Content-Disposition", "inline;fileName=" +
                        URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
                FileUtils.downloadFile(filePath + File.separator + avatar + File.separator + fileName
                        , response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
