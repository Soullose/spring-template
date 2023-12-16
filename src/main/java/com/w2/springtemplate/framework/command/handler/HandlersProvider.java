package com.w2.springtemplate.framework.command.handler;

import com.w2.springtemplate.framework.command.Command;
import com.w2.springtemplate.framework.command.CommandHandler;

//@Component
public interface HandlersProvider {

	<R, C extends Command<R>> CommandHandler getHandler(Object command);
}
