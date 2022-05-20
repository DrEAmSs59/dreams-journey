package com.dreamss.dreamjourneycommon.supports;

import com.dreamss.dreamjourneycommon.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author DrEAmSs
 */
@Setter
@Getter
@NoArgsConstructor
public class Response<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <E> Response<E> createSuccess(E data) {
        Response<E> resultVO = new Response<>();
        resultVO.setCode(ResponseCodeEnum.SUCCESS.getValue());
        resultVO.setMsg("操作成功");
        resultVO.setData(data);
        return resultVO;
    }

    public static <E> Response<E> createSuccess() {
        return createSuccess(null);
    }

    public static <E> Response<E> createFail(String hint) {
        Response<E> resultVO = new Response<>();
        resultVO.setCode(ResponseCodeEnum.FAIL.getValue());
        resultVO.setMsg(hint);
        return resultVO;
    }

    public static <E> Response<E> createFail(ResponseCodeEnum code, String message) {
        Response<E> resultVO = new Response<>();
        resultVO.setCode(code.getValue());
        resultVO.setMsg(message);
        return resultVO;
    }
}
