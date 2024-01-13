package com.w2.springtemplate.framework.netty.study.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.springframework.stereotype.Component;

@Component
public class W2NettyServerHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {

    }
}
