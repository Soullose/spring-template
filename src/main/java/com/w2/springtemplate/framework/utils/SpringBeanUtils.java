package com.w2.springtemplate.framework.utils;

import org.springframework.context.ApplicationContext;

public class SpringBeanUtils {
    private static ApplicationContext applicationContext;


    public SpringBeanUtils() {
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext != null ? applicationContext.getBean(name) : null;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext != null ? applicationContext.getBean(requiredType) : null;
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext != null ? applicationContext.getBean(name, requiredType) : null;
    }
}
