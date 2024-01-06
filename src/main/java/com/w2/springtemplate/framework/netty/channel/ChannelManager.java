package com.w2.springtemplate.framework.netty.channel;

import io.netty.channel.Channel;

import java.util.Collection;

public interface ChannelManager {
    Collection<Channel> getChannels();

    void removeChannel(Channel var1);

    void addChannel(Channel var1);

    Channel getChannel(String var1);

    String getKey(Channel var1);

    void close();
}
