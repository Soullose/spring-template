package com.w2.springtemplate.framework.shiro.filter;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

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
			log.error("Authentication required: sending 401 Authentication challenge response.");
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
			Throwable cause = e.getCause();
			if (ObjectUtils.isNotEmpty(cause) && cause instanceof ExpiredCredentialsException) {
				ExpiredCredentialsException ex = (ExpiredCredentialsException) cause;
				log.error(ex.getMessage());
			}
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

	/**
	 * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
	 */
	// private boolean refreshToken(ServletRequest request, ServletResponse
	// response) {
	// // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
	// String token = this.getAuthzHeader(request);
	// // 获取当前Token的帐号信息
	// String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
	// // 判断Redis中RefreshToken是否存在
	// if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
	// // Redis中RefreshToken还存在，获取RefreshToken的时间戳
	// String currentTimeMillisRedis =
	// JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN +
	// account).toString();
	// // 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
	// if (JwtUtil.getClaim(token,
	// Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
	// // 获取当前最新时间戳
	// String currentTimeMillis = String.valueOf(System.currentTimeMillis());
	// // 读取配置文件，获取refreshTokenExpireTime属性
	// PropertiesUtil.readProperties("config.properties");
	// String refreshTokenExpireTime =
	// PropertiesUtil.getProperty("refreshTokenExpireTime");
	// //
	// 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
	// JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account,
	// currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
	// // 刷新AccessToken，设置时间戳为当前最新时间戳
	// token = JwtUtil.sign(account, currentTimeMillis);
	// // 将新刷新的AccessToken再次进行Shiro的登录
	// JwtToken jwtToken = new JwtToken(token);
	// // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
	// this.getSubject(request, response).login(jwtToken);
	// // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
	// HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
	// httpServletResponse.setHeader("Authorization", token);
	// httpServletResponse.setHeader("Access-Control-Expose-Headers",
	// "Authorization");
	// return true;
	// }
	// }
	// return false;
	// }
}
