package com.w2.springtemplate.framework.event.guava;

import com.google.common.eventbus.EventBus;

/**
 * @author wsfzj 2024/10/22
 * @version 1.0
 * @description TODO
 */
public class EventBusFactory {

    private static EventBus eventBus;

    /**
     * 同步事件单例模式
     * @return
     */
    private static synchronized EventBus getEventBus() {
        if(eventBus == null) {
            eventBus = new EventBus();
        }
        return eventBus;
    }

    /**
     * 监听器注册
     * @param object
     */
    public static void register(Object object) {
        getEventBus().register(object);
//        getAsyncEventBus().register(object);
    }

    /**
     * 同步发送事件
     * @param event
     */
    public static void post(Object event) {
        getEventBus().post(event);
    }
}
