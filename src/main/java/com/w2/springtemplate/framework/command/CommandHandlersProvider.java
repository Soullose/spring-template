package com.w2.springtemplate.framework.command;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.w2.springtemplate.framework.command.handler.HandlersProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommandHandlersProvider implements HandlersProvider, ApplicationListener<ContextRefreshedEvent>, Ordered {

	private final ConfigurableListableBeanFactory beanFactory;

	public CommandHandlersProvider(ConfigurableListableBeanFactory beanFactory){
		this.beanFactory = beanFactory;
	}

	private Map<Class<? extends Command<?>>, String> handlers = Maps.newHashMap();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		handlers.clear();
		String[] commandHandlersNames = beanFactory.getBeanNamesForType(CommandHandler.class);
		for (String beanName : commandHandlersNames) {
			BeanDefinition commandHandler = beanFactory.getBeanDefinition(beanName);
			try {
				Class<CommandHandler<?, ?>> handlerClass = (Class<CommandHandler<?, ?>>) Class
						.forName(commandHandler.getBeanClassName());
				handlers.put(getHandledCommandType(handlerClass), beanName);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		if (log.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer("\n\nCommand Handler [\n\n");
			handlers.forEach((k, v) -> {
				sb.append(String.format("%s: %s%n", StringUtils.leftPad(v, 32), k));
			});
			sb.append("\n]");

			log.debug(sb.toString());
		}
	}

	private Class<? extends Command<?>> getHandledCommandType(Class<?> clazz) {
		Type[] genericInterfaces = clazz.getGenericInterfaces();
		ParameterizedType type = findByRawType(genericInterfaces, CommandHandler.class);
		return (Class<? extends Command<?>>) type.getActualTypeArguments()[1];
	}

	private ParameterizedType findByRawType(Type[] genericInterfaces, Class<?> expectedRawType) {
		for (Type type : genericInterfaces) {
			if (type instanceof ParameterizedType) {
				ParameterizedType parametrized = (ParameterizedType) type;
				if (expectedRawType.equals(parametrized.getRawType())) {
					return parametrized;
				}
			}
		}
		throw new RuntimeException();
	}

	@Override
	public <R, C extends Command<R>> CommandHandler getHandler(Object command) {
		String beanName = handlers.get(command.getClass());
		if (beanName == null) {
			throw new RuntimeException("command handler not found. Command class is " + command.getClass());
		}
		return beanFactory.getBean(beanName, CommandHandler.class);
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
