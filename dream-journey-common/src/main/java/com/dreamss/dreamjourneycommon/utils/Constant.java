package com.dreamss.dreamjourneycommon.utils;

import java.util.List;

/**
 * @author Created by DrEAmSs on 2022-05-20 16:49
 * 系统常量
 */
public class Constant {

    /**
     * 不参加过滤的接口列表，网关前缀+接口路径
     */
    public static final List<String> DO_NOT_FILTER = List.of("/api/user/login", "/api/user/registerOrUpdate");

    /**
     * token
     */
    public static final String TOKEN = "token";
}
