package com.w2.springtemplate.framework.vfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileSystemException;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Slf4j
public class ApacheVfsProtocolResolver implements ProtocolResolver {


    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        log.debug("ApacheVfsProtocolResolver");
        return StringUtils.startsWith(location, ApacheVfsConstants.VFS_PROTOCOL) ? new ApacheVfsResource(location) : null;
    }
}
