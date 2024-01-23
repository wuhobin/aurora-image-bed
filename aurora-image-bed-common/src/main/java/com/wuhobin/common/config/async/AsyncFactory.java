package com.wuhobin.common.config.async;

import java.util.concurrent.CompletableFuture;

/**
 * 功能描述
 *
 * @author whb
 * @version 1.0
 * @date 2023-11-18 14:27:21
 */
public class AsyncFactory {


    /**
     * 异步执行
     *
     * @param runnable runnable
     */
    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }
}
