package com.w2.springtemplate.framework.shiro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class UserAccountLoginFilter extends AuthenticatingFilter {
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		log.info("httpServletRequest:{}", httpServletRequest);
		log.info("createToken");
		return null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		log.info("onAccessDenied");
		return false;
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		log.info("onLoginSuccess");
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		log.info("onLoginFailure");
		return super.onLoginFailure(token, e, request, response);
	}
}
