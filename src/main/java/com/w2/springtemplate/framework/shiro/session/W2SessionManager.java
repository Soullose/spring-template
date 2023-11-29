package com.w2.springtemplate.framework.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;

public class W2SessionManager implements SessionManager {
    @Override
    public Session start(SessionContext context) {
        return null;
    }

    @Override
    public Session getSession(SessionKey key) throws SessionException {
        return null;
    }
}
