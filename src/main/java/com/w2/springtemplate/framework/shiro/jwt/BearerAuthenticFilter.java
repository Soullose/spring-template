package com.w2.springtemplate.framework.shiro.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
public class BearerAuthenticFilter extends BearerHttpAuthenticationFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        log.info("BearerAuthenticFilter-createToken");
        return super.createToken(request, response);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        log.info("BearerAuthenticFilter-onAccessDenied");
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        log.info("BearerAuthenticFilter-onLoginSuccess");
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.info("BearerAuthenticFilter-onLoginFailure");
        return super.onLoginFailure(token, e, request, response);
    }
}
