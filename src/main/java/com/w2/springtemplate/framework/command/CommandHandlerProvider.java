package com.w2.springtemplate.framework.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandHandlerProvider implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CommandHandler) {

        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
