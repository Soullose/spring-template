package com.w2.springtemplate.command.user.handler;

import org.apache.shiro.authc.credential.PasswordService;

import com.w2.springtemplate.command.user.UpdateSysUserCommand;
import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.framework.command.CommandHandler;
import com.w2.springtemplate.framework.command.annontation.CommandHandlerAnnotation;
import com.w2.springtemplate.interfaces.user.facade.converters.SysUserDTOConverter;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;
import com.w2.springtemplate.model.params.UpdateSysUserParams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CommandHandlerAnnotation
public class UpdateSysUserCommandHandler implements CommandHandler<SysUserDTO, UpdateSysUserCommand> {

	private final SysUserService service;
//	private final PasswordEncoder passwordEncoder;
	private final PasswordService passwordService;

	public UpdateSysUserCommandHandler(SysUserService service, PasswordService passwordService) {
		this.service = service;
		this.passwordService = passwordService;
	}

	@Override
	public SysUserDTO handle(UpdateSysUserCommand command) {
		UpdateSysUserParams params = command.getParams();
		UpdateUserDTO updateUserDTO = SysUserDTOConverter.INSTANCE.toUpdateDTO(params);
		log.info("updateUserDTO={}", updateUserDTO);
		if (params.getPassword() != null) {
			String passwordEncode = passwordService.encryptPassword(params.getPassword());
			updateUserDTO.setPassword(passwordEncode);
		}
		return SysUserDTOConverter.INSTANCE.fromPO(service.update(updateUserDTO));
	}

	@Override
	public Class<UpdateSysUserCommand> getCommandType() {
		return null;
	}
}
