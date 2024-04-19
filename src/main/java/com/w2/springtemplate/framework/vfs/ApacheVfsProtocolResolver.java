package com.w2.springtemplate.framework.vfs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApacheVfsProtocolResolver implements ProtocolResolver {

	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		log.info("ApacheVfsProtocolResolver.resolve() called with location: {}", location);
		return StringUtils.startsWith(location, ApacheVfsConstants.VFS_PROTOCOL)
				? new ApacheVfsResource(location)
				: null;
	}
}
