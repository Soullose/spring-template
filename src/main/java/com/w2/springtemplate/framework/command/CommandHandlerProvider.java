package com.w2.springtemplate.framework.command;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CommandHandlerProvider {
    private Map<Class<? extends Command<?>>, CommandHandler<?, ?>> commandHandlerMap = Maps.newLinkedHashMap();

    @Autowired
    public CommandHandlerProvider(List<CommandHandler<?, ?>> commandHandlers) {
        this.commandHandlerMap = commandHandlers.stream()
                .collect(Collectors.toMap(CommandHandler::getCommandType, Function.identity()));
        log.debug("init-commandHandler:{}", this.commandHandlerMap);
    }

    public <R, C extends Command<R>> R sendCommand(C command) {
        CommandHandler<R, C> handler = (CommandHandler<R, C>) commandHandlerMap.get(command.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for command type: " + command.getClass());
        }

        return handler.handle(command);
    }
}
