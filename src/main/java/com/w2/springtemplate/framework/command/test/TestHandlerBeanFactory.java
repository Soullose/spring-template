package com.w2.springtemplate.framework.command.test;

import com.w2.springtemplate.framework.command.CommandHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;

@Slf4j
public class TestHandlerBeanFactory implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.debug("beanFactory-是否属于ConfigurableBeanFactory:{}",beanFactory instanceof ConfigurableBeanFactory);

        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(new DefaultListableBeanFactory(), true);
        classPathBeanDefinitionScanner.addIncludeFilter(new AssignableTypeFilter(CommandHandler.class));
    }
}
