package com.w2.springtemplate.command.user.handler;

import com.w2.springtemplate.command.user.ChangePasswordCommand;
import com.w2.springtemplate.domain.service.user.SysUserService;
import com.w2.springtemplate.framework.command.CommandHandler;
import com.w2.springtemplate.framework.command.annontation.CommandHandlerAnnotation;
import com.w2.springtemplate.interfaces.user.facade.converters.SysUserDTOConverter;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;


@CommandHandlerAnnotation
public class ChangePasswordCommandHandler implements CommandHandler<SysUserDTO, ChangePasswordCommand> {

    private final SysUserService service;

    public ChangePasswordCommandHandler(SysUserService service) {
        this.service = service;
    }

    @Override
    public SysUserDTO handle(ChangePasswordCommand command) {
        String sysUserId = command.getId();
        String newPassword = command.getPassword();
        return SysUserDTOConverter.INSTANCE.fromPO(service.changeSysUserPassword(sysUserId, newPassword));
    }

    @Override
    public Class<ChangePasswordCommand> getCommandType() {
        return null;
    }
}
