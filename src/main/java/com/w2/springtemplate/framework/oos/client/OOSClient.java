package com.w2.springtemplate.framework.oos.client;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.InputStream;
import java.util.List;


public interface OOSClient {

    /**
     * 从输入流进行读取并上传到COS
     *
     * @param inputStream 输入流
     * @param key         对象存储键
     */
    void SimpleUploadFileFromStream(InputStream inputStream, String key);

    /**
     * 从输入流进行读取并上传到COS
     *
     * @param inputStream 输入流
     * @param key         对象存储键
     * @param contentType 类型
     */
    void SimpleUploadFileFromStream(InputStream inputStream, String key, String contentType);

    /**
     * 获取对象存储的url
     *
     * @param key 对象存储的键
     * @return url
     */
    String getObjectUrl(String key);

    /**
     *
     * @param bucketName
     * @return
     */
    List<S3ObjectSummary> queryAllObjects(String bucketName);
}
