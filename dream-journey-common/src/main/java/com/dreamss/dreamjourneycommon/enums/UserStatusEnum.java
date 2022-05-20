package com.dreamss.dreamjourneycommon.enums;

import lombok.Getter;

/**
 * @author Created by DrEAmSs on 2022-05-20 17:13
 * 用户状态枚举
 */
@Getter
public enum UserStatusEnum {

    UNLOCKED(0, "正常"),
    LOCKED(1, "已封禁");

    private final Integer value;
    private final String label;

    UserStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
