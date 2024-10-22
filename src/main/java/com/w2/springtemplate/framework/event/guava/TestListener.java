package com.w2.springtemplate.framework.event.guava;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wsfzj 2024/10/22
 * @version 1.0
 * @description 测试监听器
 */
@Component
public class TestListener implements W2Listener {

	private final Logger log = LoggerFactory.getLogger(TestListener.class);

	@Subscribe
	public void doSomething1(TestEvent event) {
		log.debug("监听到事件1:{}", event);
	}

	@Subscribe
	public void doSomething2(TestEvent event) {
		log.debug("监听到事件2:{}", event);
	}

	@Subscribe
	public void doSomething3(TestEvent event) {
		log.debug("监听到事件3:{}", event);
	}
}
