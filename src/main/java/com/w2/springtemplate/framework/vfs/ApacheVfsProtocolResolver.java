package com.w2.springtemplate.framework.vfs;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ApacheVfsProtocolResolver implements ProtocolResolver {

	private FileSystemManager fileSystemManager;

	public ApacheVfsProtocolResolver() {
		try {
			this.fileSystemManager = VFS.getManager();
		} catch (FileSystemException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize VFS FileSystemManager", e);
		}
	}

	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		if (location.startsWith(ApacheVfsConstants.VFS_PROTOCOL)) {
			try {
				FileObject fileObject = fileSystemManager.resolveFile(location);
				return new ApacheVfsResource(fileObject);
			} catch (FileSystemException e) {
				throw new RuntimeException("Failed to resolve VFS resource: " + location, e);
			}
		}
		return null; // 返回null表示无法解析该协议
	}
}
