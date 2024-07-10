package com.w2.springtemplate.framework.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModbusClient {
    public static void main(String[] args) {

        byte[] bytes = {
                0x00, 0x03,
                0x00, 0x00,
                0x00, 0x06,
                0x02,
                0x03,
                0x00, 0x05,
                0x00, (byte) 0x02
        };

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(12);
        buf.writeBytes(bytes);

        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast("h1", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log2((ByteBuf) msg);
                                log.debug("{}", (ByteBuf) msg);
                                Thread.sleep(1000);
                                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(bytes));
                            }

                            // @Override
                            // public void userEventTriggered(ChannelHandlerContext ctx, Object event)
                            // throws Exception {
                            // if (event instanceof IdleStateEvent) {
                            // log.info("[userEventTriggered][发起一次心跳]");
                            //// HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
                            //// ctx.writeAndFlush(new Invocation())
                            //// .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                            // } else {
                            // super.userEventTriggered(ctx, event);
                            // }
                            // }
                        })// 空闲检测
                                .addLast(new IdleStateHandler(60, 0, 0))
                                .addLast(new ReadTimeoutHandler(3 * 60));
                    }
                });

        ChannelFuture connect = bootstrap.connect("127.0.0.1", 19527);

        connect.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                future.channel().writeAndFlush(buf);
            }
        });
    }

    private static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuffer stringBuffer = new StringBuffer(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append("write index:").append(buffer.writerIndex())
                .append("capacity:").append(buffer.capacity());

    }

    private static void log2(ByteBuf buffer) {
        String s = ByteBufUtil.hexDump(buffer);
        byte len = buffer.getByte(8);
        log.debug("接受到的Modbus 报文:{}", s);
        log.debug("接受到的Modbus 寄存器长度:{}", len);

        for (int i = 0; i < len; i += 2) {
            log.debug("值:{}", buffer.getShort(9 + i));
        }
    }
}
