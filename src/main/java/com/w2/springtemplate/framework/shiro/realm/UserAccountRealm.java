package com.w2.springtemplate.framework.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.w2.springtemplate.model.SysUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAccountRealm extends AuthorizingRealm {

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
		UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
		String username = upToken.getUsername();
		log.info("username:{}", username);
		SysUser sysUser = new SysUser();
		sysUser.setUsername("wsf");
		sysUser.setPassword("$2a$10$Pk5usESTB2rQ0sdAUG1U1ePGGAaon.CpwZd5clxowMJLEAyu3kJGO");

		return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), getName());
	}

}
