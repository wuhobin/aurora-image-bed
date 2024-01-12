package com.wuhobin.gateway.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/12/25 15:56
 * @description
 */
public class MonoUtils {

    public static Mono<Void> response(ServerHttpResponse response, Object msg) {
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

    public static String getClientIp(ServerWebExchange exchange) {
        // 从ServerWebExchange中获取请求的IP地址
        String clientIp = exchange.getRequest()
                .getHeaders()
                .getFirst("X-Forwarded-For");

        // 如果X-Forwarded-For为空，则使用RemoteAddress作为备选项
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = exchange.getRequest()
                    .getRemoteAddress()
                    .getAddress()
                    .getHostAddress();
        }

        return clientIp;
    }


}
