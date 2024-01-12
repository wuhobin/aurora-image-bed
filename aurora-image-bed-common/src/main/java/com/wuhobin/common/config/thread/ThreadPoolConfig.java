package com.wuhobin.common.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wuhongbin
 * @description: 自定义线程池
 * @datetime 2023/05/08 12:18
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 核心线程池大小
     */
    private final int corePoolSize = 50;

    /**
     * 最大可创建的线程数
     */
    private final int maxPoolSize = 200;

    /**
     * 队列最大长度
     */
    private final int queueCapacity = 1000;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private final int keepAliveSeconds = 300;


    @Bean(name = "threadPoolExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
