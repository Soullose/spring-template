
package com.w2.springtemplate.framework.vfs;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ApacheVfsProtocolResolver implements ProtocolResolver {

	private final static Logger log = LoggerFactory.getLogger(ApacheVfsProtocolResolver.class);

	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		if (location == null || resourceLoader == null) {
			log.warn("Location or ResourceLoader is null");
		}
//		log.info("ApacheVfsProtocolResolver.resolve() called with location: {}", location);
		return StringUtils.startsWith(location, ApacheVfsConstants.VFS_PROTOCOL) ? new ApacheVfsResource(location)
				: null;
	}
}
