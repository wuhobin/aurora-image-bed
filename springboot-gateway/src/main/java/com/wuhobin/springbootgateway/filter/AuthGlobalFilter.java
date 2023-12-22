package com.wuhobin.springbootgateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuhobin.common.api.CommonResult;
import com.wuhobin.springbootgateway.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * 实现了GlobalFilter接口的filter 全局生效，不需要在配置中配置
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/12/21 18:05
 * @description
 */
@Configuration
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("请求地址： {}", path);
        if (Arrays.asList(securityProperties.getHttpUrls()).contains(path)){
            log.info("当前请求地址是白名单地址，直接放行： {}", path);
            return chain.filter(exchange);
        }
        if (StringUtils.isEmpty(request.getHeaders().getFirst("token"))){
            return response(exchange.getResponse(), CommonResult.failed("未登录，请先登录"));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> response(ServerHttpResponse response, Object msg) {
        String resJson = "";
        try {
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            resJson = new ObjectMapper().writeValueAsString(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(resJson.getBytes());
        return response.writeWith(Flux.just(dataBuffer));
    }
}
