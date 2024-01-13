package com.w2.springtemplate.framework.netty.study.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 服务端 Channel 实现类，提供对客户端 Channel 建立连接、断开连接、异常时的处理
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class W2NettyServerHandler extends ChannelInboundHandlerAdapter {
}
