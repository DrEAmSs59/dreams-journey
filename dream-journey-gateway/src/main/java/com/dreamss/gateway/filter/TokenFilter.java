package com.dreamss.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.dreamss.dreamjourneycommon.enums.ResponseCodeEnum;
import com.dreamss.dreamjourneycommon.supports.Response;
import com.dreamss.dreamjourneycommon.utils.Constant;
import com.dreamss.dreamjourneycommon.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Created by DrEAmSs on 2022-05-20 15:05
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (Constant.DO_NOT_FILTER.contains(exchange.getRequest().getURI().getPath())) {
            return chain.filter(exchange);
        }
        if (Objects.isNull(exchange.getRequest().getHeaders().get(Constant.TOKEN))) {
            return unAuth(exchange.getResponse(), ResponseCodeEnum.UNAUTHORIZED.getLabel());
        } else {
            try {
                String token = Objects.requireNonNull(exchange.getRequest().getHeaders().get(Constant.TOKEN)).get(0);
                JwtUtils.verifyToken(token, JwtUtils.getAudience(token));
            } catch (Exception e) {
                log.error(e.getMessage());
                return unAuth(exchange.getResponse(), e.getMessage());
            }
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> unAuth(ServerHttpResponse serverHttpResponse, String msg) {
        serverHttpResponse.setRawStatusCode(ResponseCodeEnum.UNAUTHORIZED.getValue());
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return serverHttpResponse
                .writeWith(Flux.just(serverHttpResponse.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8))));
    }
}
