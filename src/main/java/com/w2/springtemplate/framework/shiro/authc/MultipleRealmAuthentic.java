package com.w2.springtemplate.framework.shiro.authc;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

public class MultipleRealmAuthentic extends ModularRealmAuthenticator {
    /// 多个realm进行验证
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        AuthenticationStrategy authenticationStrategy = getAuthenticationStrategy();
        return super.doMultiRealmAuthentication(realms, token);
    }
}
