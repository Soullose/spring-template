package com.w2.springtemplate.framework.jpa;

import com.w2.springtemplate.framework.handlers.login.LoggedInUser;
import org.apache.shiro.SecurityUtils;

public class CurrentLoggedInName {


    public String getName(){
        LoggedInUser loggedInUser = (LoggedInUser) SecurityUtils.getSubject().getPrincipal();
        return loggedInUser.getName();
    }
}
