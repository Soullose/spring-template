package com.w2.springtemplate.framework.netty.study.common.dispatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2.springtemplate.framework.netty.study.common.codec.W2MsgBody;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<W2MsgBody> {

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ExecutorService executor =  Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, W2MsgBody msg) throws Exception {
        log.debug("MessageDispatcher:{}",msg);
        // 获得 type 对应的 MessageHandler 处理器
        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(msg.getType());
        // 获得  MessageHandler 处理器 的消息类
        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
        // 解析消息
        Message message = objectMapper.readValue(msg.getMessage(),messageClass);
        // 执行逻辑
        executor.submit(new Runnable() {

            @Override
            public void run() {
                // noinspection unchecked
                messageHandler.execute(ctx.channel(), message);
            }

        });
    }
}
