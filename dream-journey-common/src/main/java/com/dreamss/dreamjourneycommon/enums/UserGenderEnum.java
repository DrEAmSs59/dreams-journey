package com.dreamss.dreamjourneycommon.enums;

import lombok.Getter;

/**
 * @author Created by DrEAmSs on 2022-05-20 17:14
 * 用户性别枚举
 */
@Getter
public enum UserGenderEnum {

    MALE(0, "男"),
    FEMALE(1, "女"),
    OTHER(2, "其他");

    private final Integer value;
    private final String label;

    UserGenderEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
