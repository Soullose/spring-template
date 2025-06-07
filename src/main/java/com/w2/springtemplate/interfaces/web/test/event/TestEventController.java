package com.w2.springtemplate.interfaces.web.test.event;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w2.springtemplate.framework.event.guava.EventBusFactory;
import com.w2.springtemplate.framework.event.guava.TestEvent;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wsfzj 2024/10/22
 * @version 1.0
 * @description TODO
 */
@Tag(name = "测试事件接口")
@RestController
@RequestMapping("/testEvent")
@Slf4j
public class TestEventController {

	@GetMapping("/test1")
	public void test() {
		TestEvent event = TestEvent.builder().code("test").name("test").build();
		EventBusFactory.post(event);
	}
}
