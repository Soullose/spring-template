package com.w2.springtemplate.framework.shiro.session;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class W2Session implements Session {
    @Override
    public Serializable getId() {
        return null;
    }

    @Override
    public Date getStartTimestamp() {
        return null;
    }

    @Override
    public Date getLastAccessTime() {
        return null;
    }

    @Override
    public long getTimeout() throws InvalidSessionException {
        return 0;
    }

    @Override
    public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException {

    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void touch() throws InvalidSessionException {

    }

    @Override
    public void stop() throws InvalidSessionException {

    }

    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        return null;
    }

    @Override
    public Object getAttribute(Object key) throws InvalidSessionException {
        return null;
    }

    @Override
    public void setAttribute(Object key, Object value) throws InvalidSessionException {

    }

    @Override
    public Object removeAttribute(Object key) throws InvalidSessionException {
        return null;
    }
}
