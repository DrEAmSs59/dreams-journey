package com.dreamss.dreamjourneycommon.excepitons;

import com.dreamss.dreamjourneycommon.enums.ResponseCodeEnum;

import java.io.Serial;

/**
 * @author Created by DrEAmSs on 2022-05-20 10:57
 */
public class DreamException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7199535016455402399L;
    private Integer code;

    public DreamException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getLabel());
        this.code = responseCodeEnum.getValue();
    }

    public DreamException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
