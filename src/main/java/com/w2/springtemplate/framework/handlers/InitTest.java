package com.w2.springtemplate.framework.handlers;

import com.w2.springtemplate.framework.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;



@Data
public class InitTest implements Command<Test> {
    private HttpServletRequest request;
}
