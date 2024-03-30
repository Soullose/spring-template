package com.w2.springtemplate.command.user.handler;

import org.apache.shiro.authc.credential.PasswordService;

import com.w2.springtemplate.command.user.CheckPasswordCommand;
import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.framework.command.CommandHandler;
import com.w2.springtemplate.framework.command.annontation.CommandHandlerAnnotation;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.interfaces.user.facade.converters.SysUserDTOConverter;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;

@CommandHandlerAnnotation
public class CheckPasswordCommandHandler implements CommandHandler<SysUserDTO, CheckPasswordCommand> {

	private final SysUserService service;
//	private final PasswordEncoder passwordEncoder;

	private final PasswordService passwordService;

	public CheckPasswordCommandHandler(SysUserService sysUserService,PasswordService passwordService) {
		this.service = sysUserService;
		this.passwordService = passwordService;
	}

	@Override
	public SysUserDTO handle(CheckPasswordCommand command) {
		/// 校验密码是否正确
		String username = command.getUsername();
		String password = command.getPassword();
		SysUser sysUser = service.findOneByUsername(username);

		if (passwordService.passwordsMatch(password, sysUser.getPassword())) {
			return SysUserDTOConverter.INSTANCE.fromPO(sysUser);
		}else {
			throw new RuntimeException("密码错误");
		}
	}

	@Override
	public Class<CheckPasswordCommand> getCommandType() {
		return null;
	}
}
