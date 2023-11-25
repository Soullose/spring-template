package com.w2.springtemplate.framework.shiro.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
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
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		log.info("BearerAuthenticFilter-onAccessDenied");
		boolean loggedIn = false; // false by default or we wouldn't be in this method
		if (isLoginAttempt(request, response)) {
			log.info("----====----");
			loggedIn = this.executeLogin(request, response);
		}
		if (!loggedIn) {
			sendChallenge(request, response);
			log.error("request header 未带Authorization 参数");
		}
		return loggedIn;
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		String authorizationHeader = getAuthzHeader(request);
		String jwt = getPrincipalsAndCredentials(authorizationHeader, request)[0];
		log.info("jwt:{}", jwt);
		if (authorizationHeader == null) {
			String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
					+ "must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}
		BearerToken bearerToken = new BearerToken(jwt, request.getRemoteHost());
		try {
			Subject subject = getSubject(request, response);
			subject.login(bearerToken);
			return onLoginSuccess(bearerToken, subject, request, response);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return onLoginFailure(bearerToken, e, request, response);
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		log.info("BearerAuthenticFilter-onLoginSuccess");
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		log.info("BearerAuthenticFilter-onLoginFailure");
		return super.onLoginFailure(token, e, request, response);
	}
}
