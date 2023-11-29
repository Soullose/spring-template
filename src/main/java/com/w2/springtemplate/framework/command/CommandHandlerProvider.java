package com.w2.springtemplate.framework.command;

import org.springframework.beans.factory.BeanFactory;

public class CommandHandlerProvider<H extends CommandHandler<?, ?>> {

    private final Class<H> type;

    private final BeanFactory beanFactory;

    public CommandHandlerProvider(BeanFactory beanFactory, Class<H> type) {
        this.beanFactory = beanFactory;
        this.type = type;
    }

    public H getHandler() {
        return beanFactory.getBean(type);
    }
}
