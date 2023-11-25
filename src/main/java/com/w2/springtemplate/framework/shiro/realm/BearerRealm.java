package com.w2.springtemplate.framework.shiro.realm;

import com.w2.springtemplate.framework.shiro.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class BearerRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        log.info("BearerRealm-Authentication");

        try {
            Claims claims = JwtUtil.parseJWT(token.getCredentials().toString());

            log.debug("BearerRealm-claims-{}", claims);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
