package com.w2.springtemplate.framework.command;

/// 命令处理器
public interface CommandHandler<R, C extends Command<R>> {
    R handle(C command);
    Class<C> getCommandType();
}
