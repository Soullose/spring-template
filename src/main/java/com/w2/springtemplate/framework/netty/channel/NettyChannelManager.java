package com.w2.springtemplate.framework.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *  netty通道管理
 *  @author w2
 */
@Slf4j
@Component
public class NettyChannelManager implements ChannelManager{

    /**
     * Channel 映射
     */
    private ConcurrentMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();


    /**
     * ModbusChannel 映射
     */
    private ConcurrentMap<String, Channel> modbusChannels = new ConcurrentHashMap<>();


    protected ChannelGroup clientChannels;

    public NettyChannelManager(){
        log.debug("NettyChannelManager:{}",this.getClass().getSimpleName());
        this.clientChannels = new DefaultChannelGroup(this.getClass().getSimpleName(), GlobalEventExecutor.INSTANCE);
    }

    @Override
    public Collection<Channel> getChannels() {
        return null;
    }

    @Override
    public void removeChannel(Channel var1) {

    }

    @Override
    public void addChannel(Channel var1) {

    }

    @Override
    public Channel getChannel(String var1) {
        return null;
    }

    @Override
    public String getKey(Channel var1) {
        return null;
    }

    @Override
    public void close() {

    }
}
