package com.w2.springtemplate.framework.command;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
//@Component
public class CommandHandlerBeanFactory implements BeanFactoryPostProcessor {

    private ConfigurableBeanFactory beanFactory;

    private final DefaultListableBeanFactory handlerBeanFactory = new DefaultListableBeanFactory();


    private final Map<Class<? extends Command>, CommandHandlerProvider> handlers = Maps.newHashMap();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;

        this.handlerBeanFactory.setParentBeanFactory(this.beanFactory);

        // Required so that all BeanPostProcessors, Scopes, etc become available.
        this.handlerBeanFactory.copyConfigurationFrom(this.beanFactory);

        // 由于自己已经被添加所以无需添加自己 @see resetFilters
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(handlerBeanFactory, false);
        scanner.setIncludeAnnotationConfig(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(CommandHandler.class));
        scanner.getBeanDefinitionDefaults().setLazyInit(false);

        Set<String> packages = Sets.newHashSet();

        // 默认扫描自己的路径
        packages.add(this.getClass().getPackage().getName());

        scanner.scan(packages.toArray(new String[0]));

        Stream.of(handlerBeanFactory.getBeanDefinitionNames()).forEach(beanName -> {
            Class<?> beanClass = handlerBeanFactory.getType(beanName);

//            assert beanClass != null;
            if (CommandHandler.class.isAssignableFrom(beanClass)) {
                Class<CommandHandler<?, ?>> handlerClass = (Class<CommandHandler<?, ?>>) beanClass;
                Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, CommandHandler.class);
//                assert generics != null;
                Class<? extends Command> commandType = (Class<? extends Command>) generics[1];
                handlers.put(commandType, new CommandHandlerProvider(handlerBeanFactory, handlerClass));

                if (log.isDebugEnabled()) {
                    log.debug("{} for command {} has been registed to {}", handlerClass.getSimpleName(),
                            commandType.getSimpleName());
                }
            }
        });
    }


    public <R, C extends Command<R>> CommandHandler<R, C> lookup(Class<C> clazz) {
        return handlers.get(clazz).getHandler();
    }

}
