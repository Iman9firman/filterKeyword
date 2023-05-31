package com.filter.keyword.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@EnableAsync
@Configuration
@Slf4j
public class Config implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("asyncThread-");
        executor.initialize();
        return executor;
    }
    /**
     * @Override {AsyncUncaughtExceptionHandler} getAsyncUncaughtExceptionHandler - merubah pesan exception pada async.
     * @return {new} AsyncUncaughtExceptionHandler menerapkan pesan exception baru pada async.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                System.out.println("Throwable Exception message : " + ex.getMessage());
                System.out.println("Method name                 : " + method.getName());
                for (Object param : params) {
                    System.out.println("Parameter value             : " + param);
                }
                System.out.println("stack Trace ");
                ex.printStackTrace();
            }

        };
    }
}
