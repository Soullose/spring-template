package com.w2.springtemplate.command.user.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.w2.springtemplate.command.user.RegisterSysUserCommand;
import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.framework.command.CommandHandler;
import com.w2.springtemplate.framework.command.annontation.CommandHandlerAnnotation;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.model.converters.user.UserApplicationConverter;
import com.w2.springtemplate.model.dto.RegisterUserDTO;
import com.w2.springtemplate.utils.crypto.PasswordEncoder;

@CommandHandlerAnnotation
public class RegisterSysUserCommandHandler implements CommandHandler<RegisterUserDTO, RegisterSysUserCommand> {

    @Autowired
    private SysUserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserDTO handle(RegisterSysUserCommand command) {
        RegisterUserDTO registerUserDTO = UserApplicationConverter.INSTANCE.toDTO(command.getParams());
        registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        SysUser register = service.register(registerUserDTO);
        return UserApplicationConverter.INSTANCE.fromPO(register);
    }

    @Override
    public Class<RegisterSysUserCommand> getCommandType() {
        return null;
    }
}
