package com.w2.springtemplate.framework.shiro.realm;

import com.w2.springtemplate.framework.shiro.model.LoggedInUser;
import com.w2.springtemplate.model.QSysUser;
import com.w2.springtemplate.model.SysUser;
import com.w2.springtemplate.repository.SysUserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.w2.springtemplate.framework.shiro.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BearerRealm extends AuthorizingRealm {

    @Autowired
    private SysUserRepository repository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        log.info("BearerRealm-Authentication");
        BearerToken upToken = (BearerToken) token;
        log.info("upToken:{}",upToken.getPrincipal());
        log.info("getToken:{}",upToken.getToken());
        log.info("getHost:{}",upToken.getHost());
        log.info("getCredentials:{}",upToken.getCredentials());
        QSysUser qSysUser = QSysUser.sysUser;
        try {
            Claims claims = JwtUtil.parseJWT(token.getCredentials().toString());

            log.debug("BearerRealm-claims-{}", claims);
            log.info(claims.getId());
            log.info(claims.getSubject());
            SysUser sysUser = repository.findOne(qSysUser.username.eq(claims.getSubject())).orElseThrow(NullPointerException::new);

            String password = sysUser.getPassword();
            LoggedInUser loggedInUser = new LoggedInUser();
            BeanUtils.copyProperties(sysUser, loggedInUser);
            loggedInUser.setHost(upToken.getHost());

            return new SimpleAuthenticationInfo(loggedInUser.toMap(), upToken.getPrincipal(), getName());
        } catch (ExpiredJwtException e) {
//			e.printStackTrace();
            throw new ExpiredCredentialsException(e);
        }
    }
}
