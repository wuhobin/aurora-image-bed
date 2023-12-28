package com.wuhobin.springbootgateway.handler;

import com.wuhobin.common.api.CommonResult;
import com.wuhobin.springbootgateway.utils.MonoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 自定义触发限流时的返回异常
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/12/28 16:34
 * @description
 */
public class SentinelFallbackHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return MonoUtils.response(serverHttpResponse, CommonResult.failed("系统繁忙，请稍后重试！"));
    }
}
