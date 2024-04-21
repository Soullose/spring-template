package com.w2.springtemplate.command.user.handler;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;

import com.w2.springtemplate.command.user.RegisterSysUserCommand;
import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.framework.command.CommandHandler;
import com.w2.springtemplate.framework.command.annontation.CommandHandlerAnnotation;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.interfaces.user.facade.converters.SysUserDTOConverter;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CommandHandlerAnnotation
public class RegisterSysUserCommandHandler implements CommandHandler<SysUserDTO, RegisterSysUserCommand> {

    @Autowired
    private SysUserService service;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordService passwordService;

    @Override
    public SysUserDTO handle(RegisterSysUserCommand command) {
        log.debug("command: {}", command);
        RegisterUserDTO registerUserDTO = SysUserDTOConverter.INSTANCE.toDTO(command.getParams());
        // registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        registerUserDTO.setPassword(passwordService.encryptPassword(registerUserDTO.getPassword()));
        SysUser register = service.register(registerUserDTO);
        return SysUserDTOConverter.INSTANCE.fromPO(register);
    }

    @Override
    public Class<RegisterSysUserCommand> getCommandType() {
        return null;
    }
}
