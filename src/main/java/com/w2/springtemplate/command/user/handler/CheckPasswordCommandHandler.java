package com.w2.springtemplate.command.user.handler;

import com.w2.springtemplate.command.user.CheckPasswordCommand;
import com.w2.springtemplate.framework.command.CommandHandler;
import com.w2.springtemplate.framework.command.annontation.CommandHandlerAnnotation;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;

@CommandHandlerAnnotation
public class CheckPasswordCommandHandler implements CommandHandler<SysUserDTO, CheckPasswordCommand> {
	@Override
	public SysUserDTO handle(CheckPasswordCommand command) {
		/// 校验密码是否正确
		return null;
	}

	@Override
	public Class<CheckPasswordCommand> getCommandType() {
		return null;
	}
}
