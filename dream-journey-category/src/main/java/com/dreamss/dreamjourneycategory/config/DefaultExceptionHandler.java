package com.dreamss.dreamjourneycategory.config;

import com.dreamss.dreamjourneycommon.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author Created by DrEAmSs on 2022-04-25 18:41
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 空指针异常全局处理
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> nullPointerException(HttpServletRequest request, Exception e) {
        this.logError(request, e);
        return ResponseEntity.internalServerError().body(ResponseCodeEnum.SERVER_ERROR.getLabel());
    }

    /**
     * SQL异常全局处理
     */
    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<?> SqlException(HttpServletRequest request, Exception e) {
        this.logError(request, e);
        return ResponseEntity.internalServerError().body(ResponseCodeEnum.SERVER_ERROR.getLabel());
    }

    /**
     * 其他异常统一处理
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exception(HttpServletRequest request, Exception e) {
        this.logError(request, e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * 记录错误日志
     */
    private void logError(HttpServletRequest request, Exception e) {
        log.error("path: {}, queryParam: {}, errorMessage: {}",
                request.getRequestURI(), request.getQueryString(), e.getMessage());
    }
}
