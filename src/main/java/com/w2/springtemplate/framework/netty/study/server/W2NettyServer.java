package com.w2.springtemplate.framework.netty.study.server;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.w2.springtemplate.framework.netty.study.server.handler.W2NettyServerHandlerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class W2NettyServer {

	/// 端口号
	private static final Integer PORT = 19527;

	@Autowired
	private W2NettyServerHandlerInitializer nettyServerHandlerInitializer;
	/**
	 * boss 线程组，用于服务端接受客户端的连接
	 */
	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	/**
	 * worker 线程组，用于服务端接受客户端的数据读写
	 */
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();
	/**
	 * Netty Server Channel
	 */
	private Channel channel;

	/// 启动
	@PostConstruct
	public void start() throws InterruptedException {
		// 创建 ServerBootstrap 对象，用于 Netty Server 启动
		ServerBootstrap bootstrap = new ServerBootstrap();
		// 设置 ServerBootstrap 的各种属性
		bootstrap.group(bossGroup, workerGroup) // 设置两个 EventLoopGroup 对象
				.channel(NioServerSocketChannel.class) // 指定 Channel 为服务端 NioServerSocketChannel
				.localAddress(new InetSocketAddress(PORT)) // 设置 Netty Server 的端口
				.option(ChannelOption.SO_BACKLOG, 1024) // 服务端 accept 队列的大小
				.childOption(ChannelOption.SO_KEEPALIVE, true) // TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
				.childOption(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
				.childHandler(nettyServerHandlerInitializer);
		// 绑定端口，并同步等待成功，即启动服务端
		ChannelFuture future = bootstrap.bind(PORT).sync();
		if (future.isSuccess()) {
			channel = future.channel();
			log.debug("[start][Netty Server 启动在 {} 端口]", PORT);
		}
	}

	@PreDestroy
	public void shutdown() {
		// 关闭 Netty Server
		if (channel != null) {
			channel.close();
		}
		// 优雅关闭两个 EventLoopGroup 对象
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		log.debug("[shutDown][Netty Server 端口 {} 关闭]", PORT);
	}
}
