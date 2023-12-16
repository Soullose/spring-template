package com.w2.springtemplate.framework.handlers.login;

import com.w2.springtemplate.framework.command.annontation.CommandHandlerAnnotation;
import org.apache.shiro.SecurityUtils;

import com.w2.springtemplate.framework.command.CommandHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CommandHandlerAnnotation
public class LoggedInCommandHandler implements CommandHandler<LoggedInUser, LoggedInCommand> {
	@Override
	public LoggedInUser handle(LoggedInCommand command) {
		log.debug("LoggedInCommandHandler-LoggedInUser:{}", (LoggedInUser) SecurityUtils.getSubject().getPrincipal());
		return (LoggedInUser) SecurityUtils.getSubject().getPrincipal();
	}

	@Override
	public Class<LoggedInCommand> getCommandType() {
		return null;
	}
}
