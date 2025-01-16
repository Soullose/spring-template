package com.w2.springtemplate.framework.event.guava;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

/**
 * @author wsfzj 2024/10/22
 * @version 1.0
 * @description 注册事件监听器
 */
@Component
public class W2EventBusProvider implements ApplicationListener<ContextRefreshedEvent> {

	private final Logger log = LoggerFactory.getLogger(W2EventBusProvider.class);

	private final ConfigurableListableBeanFactory beanFactory;

	public W2EventBusProvider(ConfigurableListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	private final Map<Class<? extends W2Listener>, String> listeners = Maps.newLinkedHashMap();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		listeners.clear();
		String[] listenerNames = beanFactory.getBeanNamesForType(W2Listener.class);
		for (String listenerName : listenerNames) {
			// BeanDefinition = beanFactory.getBeanDefinition(listenerName);
			try {
				W2Listener listener = beanFactory.getBean(listenerName, W2Listener.class);
				listeners.put(listener.getClass(), listenerName);
				// EventBus eventBus = new EventBus();
				// eventBus.register(listener);
				EventBusFactory.register(listener);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		if (log.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer("\n\nEvent Listeners [\n\n");
			listeners.forEach((k, v) -> {
				sb.append(String.format("%s: %s%n", StringUtils.leftPad(v, 32), k));
			});
			sb.append("\n]");

			log.debug(sb.toString());
		}
	}
}
