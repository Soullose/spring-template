package com.w2.springtemplate.framework.shiro.authc;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class MultipleRealmAuthentic extends ModularRealmAuthenticator {

    private final Realm userAccountRealm;

    private final Realm bearerRealm;

    public MultipleRealmAuthentic(Realm userAccountRealm,Realm bearerRealm){
        this.userAccountRealm = userAccountRealm;
        this.bearerRealm = bearerRealm;
    }


    @Override
    public void setRealms(Collection<Realm> realms) {
        List<Realm> multiRealm = Arrays.asList(bearerRealm, userAccountRealm);
        super.setRealms(multiRealm);
    }

    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        return super.doMultiRealmAuthentication(realms, token);
    }
}
