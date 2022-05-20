package com.dreamss.dreamjourneycommon.enums;

import lombok.Getter;

/**
 * @author DrEAmSs
 */

@Getter
public enum ResponseCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    UNAUTHORIZED(401, "用户未登录"),
    NOT_FOUND(404, "未找到URL"),
    SERVER_ERROR(500, "服务器内部错误"),
    TIMEOUT(408, "请求超时");

    private final Integer value;
    private final String label;

    ResponseCodeEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }
}
