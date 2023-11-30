package com.w2.springtemplate.framework.handlers;

import com.w2.springtemplate.framework.command.Command;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class InitTest implements Command<Test> {
    private HttpServletRequest request;
}
