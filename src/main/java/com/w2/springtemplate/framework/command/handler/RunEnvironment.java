package com.w2.springtemplate.framework.command.handler;

import com.w2.springtemplate.framework.command.Command;
import com.w2.springtemplate.framework.command.CommandHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class RunEnvironment {

    @Inject
    private HandlersProvider handlerProvider;


    public <R, C extends Command<R>> R run(C command) {
        CommandHandler<R, C> handler = handlerProvider.getHandler(command);

        //You can add Your own capabilities here: dependency injection, security, transaction management, logging, profiling, spying, storing commands, etc

//        Object result = handler.handle(command);

        //You can add Your own capabilities here

        return handler.handle(command);
    }

}
