package com.w2.springtemplate.framework.oos.client;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.w2.springtemplate.framework.oos.constant.SangForOOS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/// 深信服
@ConditionalOnClass(value = AmazonS3.class)
@Service(value = "sfOOSClient")
@Slf4j
public class SangForOOSClient implements OOSClient {


    private static final String BUCKET_NAME = "md-1309044032";

    protected AmazonS3 sfS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(SangForOOS.ACCESS_KEY, SangForOOS.SECRET_KEY);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP); // 使用http访问
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(clientConfig)
                .withEndpointConfiguration(
                        new AwsClientBuilder
                                .EndpointConfiguration(SangForOOS.ENDPOINT, Region.CN_Beijing.name()))
                .withPathStyleAccessEnabled(true).build();
    }

    @Override
    public void SimpleUploadFileFromStream(InputStream inputStream, String key) {
        ObjectMetadata metadata = new ObjectMetadata();
        // 上传的文件的长度
        try {
            metadata.setContentLength(inputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sfS3Client().putObject(BUCKET_NAME, key, inputStream, metadata);
        sfS3Client().setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicReadWrite);
        shutdown();
    }

    @Override
    public void SimpleUploadFileFromStream(InputStream inputStream, String key, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        // 上传的文件的长度
        try {
            metadata.setContentLength(inputStream.available());
            metadata.setContentType(contentType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sfS3Client().putObject(BUCKET_NAME, key, inputStream, metadata);
        sfS3Client().setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicReadWrite);
        shutdown();
    }

    @Override
    public String getObjectUrl(String key) {
        return SangForOOS.ENDPOINT + "/" + key;
    }

    @Override
    public List<S3ObjectSummary> queryAllObjects(String bucketName) {
        try {
            ListObjectsV2Result result = sfS3Client().listObjectsV2(bucketName);
            List<S3ObjectSummary> objects = result.getObjectSummaries();
            for (S3ObjectSummary os : objects) {
                log.info("os {}", os);
                log.info("* {}", os.getKey());
            }
            shutdown();
            return objects;
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return null;
    }


    protected void shutdown() {
        log.info("关闭S3客户端");
        sfS3Client().shutdown();
    }
}
