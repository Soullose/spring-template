package com.w2.springtemplate.framework.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.lang.util.SimpleByteSource;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import com.w2.springtemplate.framework.handlers.login.LoggedInUser;
import com.w2.springtemplate.infrastructure.entities.QSysUser;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.infrastructure.repository.SysUserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;

@Slf4j
public class UserAccountRealm extends AuthorizingRealm {

	@Autowired
    @Lazy
	private SysUserRepository repository;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	// public UserAccountRealm() {
	// };

	// public UserAccountRealm(SysUserRepository repository) {
	// this.repository = repository;
	// };

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		return null;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		log.info("UserAccountRealm.doGetAuthenticationInfo()");
		QSysUser qSysUser = QSysUser.sysUser;
		UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
		String username = upToken.getUsername();
		log.info("username:{}", username);

		Predicate sysUserPredicate = qSysUser.username.eq(username);
		SysUser sysUser = repository.findOne(sysUserPredicate).orElseThrow(
				() -> new UnknownAccountException(String.format("User account not found, (username=%s)", username)));
		log.info("sysUser-username:{}", sysUser.getUsername());
		String password = sysUser.getPassword();
		LoggedInUser loggedInUser = new LoggedInUser();
		BeanUtils.copyProperties(sysUser, loggedInUser);
		loggedInUser.setHost(upToken.getHost());
		log.info("loggedInUser:{}", loggedInUser.toMap());
		// if (RedisUtils.hasKey("system:cache:userinfo:" + sysUser.getId())) {

		// } else {
		// RedisUtils.set("system:cache:userinfo:" + sysUser.getId(), sysUser, 3600);
		// }
		return new SimpleAuthenticationInfo(loggedInUser, password, getName());
	}


}
