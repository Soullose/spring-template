package com.w2.springtemplate.framework.vfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Slf4j
@Component
public class ApacheVfsInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.debug("ApacheVfsProtocolResolver");
        // 添加虚拟文件协议分解器
        applicationContext.addProtocolResolver(newProtocolResolver());
    }

    public ApacheVfsInitializer() {
        this.init();
    }
    /**
     * 获取虚拟根目录，如果根目录不存在则动态创建
     *
     * @return
     * @throws FileSystemException
     */
    private FileObject getOrCreateDataDir() throws FileSystemException {
        FileSystemManager fileSystemManager = VFS.getManager();
        FileObject dataDir = fileSystemManager.resolveFile(SystemUtils.getUserDir().getAbsolutePath())
                .resolveFile(ApacheVfsConstants.DATA_DIR_NAME);

        if (!dataDir.exists()) {
            log.debug("11111");
            dataDir.createFolder();
        }

        return dataDir;
    }

    /**
     * 初始化
     */
    public void init() {
        log.info("ApacheVfs初始化");
        try {
            // 创建虚拟根目录
            FileObject rootDir = VFS.getManager().createVirtualFileSystem(ApacheVfsConstants.VFS_PROTOCOL);

            // 获取本地数据目录，并将该目录下的文件夹挂载到根目录
            Stream.of(getOrCreateDataDir().getChildren()).filter((file) -> {
                try {
                    return (file.isFolder() && !file.isHidden());
                } catch (FileSystemException e) {
                    ExceptionUtils.rethrow(e);
                    return false;
                }
            }).forEach((folder) -> {
                String baseName = folder.getName().getBaseName();
                try {
                    rootDir.getFileSystem().addJunction(baseName, folder);
//					 if (log.isTraceEnabled()) {
                    log.trace("mount {} to vfs://{}", rootDir.getPublicURIString(), baseName);
//					 }
                } catch (FileSystemException e) {
                    ExceptionUtils.rethrow(e);
                }
            });

            // 挂载系统临时文件夹
            rootDir.getFileSystem().addJunction("tmp",
                    VFS.getManager().resolveFile(SystemUtils.getJavaIoTmpDir().getAbsolutePath()));

            // 将根目录挂载到虚拟文件系统
            ((DefaultFileSystemManager) VFS.getManager()).setBaseFile(rootDir);

        } catch (FileSystemException e) {
            ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 协议解析器
     */
    public ProtocolResolver newProtocolResolver() {
        return new ApacheVfsProtocolResolver();
    }
}
