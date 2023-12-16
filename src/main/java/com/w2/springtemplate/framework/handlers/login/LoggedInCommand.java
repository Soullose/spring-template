package com.w2.springtemplate.framework.handlers.login;

import com.w2.springtemplate.framework.command.Command;
import lombok.Data;

@Data
public class LoggedInCommand implements Command<LoggedInUser> {
}
