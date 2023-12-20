package com.w2.springtemplate.command.user;

import com.w2.springtemplate.framework.command.Command;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckPasswordCommand implements Command<SysUserDTO> {
}
