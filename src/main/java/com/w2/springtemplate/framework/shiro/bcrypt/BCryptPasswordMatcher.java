package com.w2.springtemplate.framework.shiro.bcrypt;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.Hash;

@Slf4j
public class BCryptPasswordMatcher implements CredentialsMatcher {

    private final PasswordService passwordService;

    public BCryptPasswordMatcher(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        Object submittedPassword = getSubmittedPassword(token);
        Object storedCredentials = getStoredPassword(info);
        assertStoredCredentialsType(storedCredentials);

        return passwordService.passwordsMatch(submittedPassword, storedCredentials.toString());
    }


    protected Object getSubmittedPassword(AuthenticationToken token) {
        log.info("token:{}", new String((char[])token.getCredentials()));
        return token != null ? token.getCredentials() : null;
    }

    protected Object getStoredPassword(AuthenticationInfo storedAccountInfo) {
        Object stored = storedAccountInfo != null ? storedAccountInfo.getCredentials() : null;
        //fix for https://issues.apache.org/jira/browse/SHIRO-363
        if (stored instanceof char[]) {
            stored = new String((char[]) stored);
        }
        return stored;
    }

    private void assertStoredCredentialsType(Object credentials) {
        if (credentials instanceof String) {
            return;
        }

        String msg = "Stored account credentials are expected to be either a " +
                Hash.class.getName() + " instance or a formatted hash String.";
        throw new IllegalArgumentException(msg);
    }
}
