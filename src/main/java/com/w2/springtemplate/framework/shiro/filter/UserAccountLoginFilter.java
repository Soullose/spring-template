package com.w2.springtemplate.framework.shiro.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2.springtemplate.framework.shiro.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class UserAccountLoginFilter extends AuthenticatingFilter {
	public static final String BEARER = "Bearer";
	private static final String AUTHORIZATION_HEADER = "Authorization";

	private static final String USERNAME_PARAM = "username";
	private static final String PASSWORD_PARAM = "password";
	private static final String REMEMBER_ME_PARAM = "rememberMe";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		log.info("createToken");
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		// 使用Jackson读取登录信息
		JsonNode jsonNode = objectMapper.readTree(httpServletRequest.getInputStream());
		log.info("jsonNode:{}", jsonNode);

		if (jsonNode.has(USERNAME_PARAM) && jsonNode.has(PASSWORD_PARAM)) {
			String username = jsonNode.get(USERNAME_PARAM).asText("");
			String password = jsonNode.get(PASSWORD_PARAM).asText("");

			boolean rememberMe = jsonNode.has(REMEMBER_ME_PARAM) && jsonNode.get(REMEMBER_ME_PARAM).asBoolean(false);

			String host = httpServletRequest.getRemoteHost();

			log.info("User login [username = {}, password = {}, rememberMe = {}, host = {}]", username, password,
					rememberMe, host);

			return new UsernamePasswordToken(username, password, rememberMe, host);
		}

		log.error("Unknown authentication token");

		return null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		log.info("onAccessDenied");
		boolean loginRequest = isLoginRequest(request, response);
		if (loginRequest) {
			if (isLoginSubmission(request)) {
				log.info("isLoginSubmission");
				return executeLogin(request, response);
			} else {
				// 地址正确，但协议不正确
				log.error("Invalid login method");
				WebUtils.toHttp(response).setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				WebUtils.toHttp(response).setHeader("Allow", "POST");
				return false;
			}
		}
		return false;
	}
	// 判断请求是否为登录请求
	protected boolean isLoginSubmission(ServletRequest request) {
		return (request instanceof HttpServletRequest)
				&& WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
	}
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		log.info("onLoginSuccess");

		log.debug("User login success");

		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		@SuppressWarnings("unchecked")
		Map<String, Object> claims = objectMapper.convertValue(subject.getPrincipal(), Map.class);

		checkNotNull(claims);
		String jwt = JwtUtil.createJWT(claims.get("username").toString(), claims);
		httpResponse.setHeader(AUTHORIZATION_HEADER, BEARER + StringUtils.SPACE + jwt);
		httpResponse.setStatus(HttpServletResponse.SC_OK);

		Claims claims1 = JwtUtil.parseJWT(jwt);
		log.info("claims1:{}", claims1);
		return true;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		log.error("onLoginFailure");
		WebUtils.toHttp(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		return false;
	}
}
