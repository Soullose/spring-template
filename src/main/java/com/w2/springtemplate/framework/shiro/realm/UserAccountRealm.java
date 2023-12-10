package com.w2.springtemplate.framework.shiro.realm;

import com.querydsl.core.types.Predicate;
import com.w2.springtemplate.framework.shiro.model.LoggedInUser;
import com.w2.springtemplate.model.QSysUser;
import com.w2.springtemplate.domain.SysUser;
import com.w2.springtemplate.infrastructure.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserAccountRealm extends AuthorizingRealm {

	@Autowired
	private SysUserRepository repository;

//	public UserAccountRealm() {
//	};

//	public UserAccountRealm(SysUserRepository repository) {
//		this.repository = repository;
//	};

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
		log.info("sysUser:{}", sysUser);
		String password = sysUser.getPassword();
		LoggedInUser loggedInUser = new LoggedInUser();
		BeanUtils.copyProperties(sysUser, loggedInUser);
		loggedInUser.setHost(upToken.getHost());
		log.info("loggedInUser:{}", loggedInUser.toMap());
		return new SimpleAuthenticationInfo(loggedInUser, password, getName());
	}

}
