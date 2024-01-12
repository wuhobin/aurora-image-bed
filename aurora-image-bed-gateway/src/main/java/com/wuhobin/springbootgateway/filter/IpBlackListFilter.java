package com.wuhobin.springbootgateway.filter;

import com.wuhobin.springbootgateway.utils.MonoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ip黑名单 局部过滤器
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/12/25 15:32
 * @description
 */
@Component
@Slf4j
public class IpBlackListFilter extends AbstractGatewayFilterFactory<IpBlackListFilter.Config> {

    public IpBlackListFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            String clientIp = MonoUtils.getClientIp(exchange);
            log.info("进入ip黑名单局部过滤器 当前请求ip={} blackIpList={}",clientIp, config.getBlackIpList());
            if (config.getBlackIpList().contains(clientIp)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private List<String> blackIpList;

        public List<String> getBlackIpList() {
            return blackIpList;
        }

        public void setBlackIpList(List<String> blackIpList) {
            this.blackIpList = blackIpList;
        }
    }
}
