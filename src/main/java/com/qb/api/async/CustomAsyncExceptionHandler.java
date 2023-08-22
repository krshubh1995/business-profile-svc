package com.qb.api.async;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error("Method name... {}", method.getName());
        log.error("Exception Message... {}", throwable.getMessage());
        for (Object param : objects) {
            log.warn("Parameter Value ..{}", param);
        }
    }
}
