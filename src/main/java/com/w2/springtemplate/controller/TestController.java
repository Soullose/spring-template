package com.w2.springtemplate.controller;

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
import com.w2.springtemplate.framework.oos.client.OOSClient;
import com.w2.springtemplate.framework.vfs.ApacheVfsResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.util.List;

@Api(tags = "测试接口")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    private ResourceLoader resourceLoader;

    public TestController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
//	@Autowired
//	private AmazonS3 sfS3Client;

    @Autowired
    @Qualifier(value = "sfOOSClient")
    private OOSClient oosClient;

    private static final String accessKey = "JZYMLZEWNAHACV6L6ILL"; // 使用EDS web界面创建的对象存储用户，此处填用户的access key
    private static final String secretKey = "QJUeJXTIw8EdBp2UirBqP4E46VsFmcF2n6UimaZB"; // 使用EDS
    // web界面创建的对象存储用户，此处填用户的secret
    // key
    private static final String endPoint = "https://oss-beijing.sangforcloud.com:12000"; // EDS对象存储的地址:对象存储服务的端口号

    @GetMapping("/testUpload")
    public String testUpload(@RequestParam String bucketName) {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP); // 使用http访问

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withClientConfiguration(clientConfig)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endPoint, Region.CN_Beijing.name()))
                .withPathStyleAccessEnabled(true).build();

        // 要上传文件的路径

        try {
            // String bucketName = "cxyjy";
            String keyName = "t1/t2/t3/日志错误提示.txt";

            // ---------------------替换成自己的数据流-----------------------------
            String filePath = "D:\\JZ-WORK\\Project\\IPMS-STUDY\\日志错误提示.txt";
            File file = new File(filePath);
            // 以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
            // ---------------------替换成自己的数据流-----------------------------

            // 文件名
            String fileName = file.getName();
            // 文件大小
            Long fileSize = file.length();
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            // 上传的文件的长度
            metadata.setContentLength(is.available());
            // 指定该Object被下载时的网页的缓存行为
            // metadata.setCacheControl("no-cache");
            // 指定该Object下设置Header
            // metadata.setHeader("Pragma", "no-cache");
            // 指定该Object被下载时的内容编码格式
            // metadata.setContentEncoding("utf-8");
            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读文件。如果用户没有指定则根据K取ey或文件名的扩展名生成，
            // 如果没有扩展名则填默认值application/octet-stream
            // metadata.setContentType("application/octet-stream");
            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            // metadata.setContentDisposition("filename/filesize=" + fileName + "/" +
            // fileSize + "Byte.");

            s3Client.putObject(bucketName, keyName, is, metadata);

            s3Client.setObjectAcl(bucketName, keyName, CannedAccessControlList.PublicReadWrite);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "hello";
    }

    @GetMapping("/queryAllBuckets")
    public ResponseEntity<List<Bucket>> queryAllBuckets() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS); // 使用http访问

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withClientConfiguration(clientConfig)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endPoint, Region.CN_Beijing.name()))
                .build();

        List<Bucket> buckets = s3Client.listBuckets();
        for (Bucket bucket : s3Client.listBuckets()) {
            log.info("bucket - {}", bucket.getName());
        }
        return ResponseEntity.ok(buckets);
    }

    @GetMapping("/createBucket")
    public ResponseEntity<List<Bucket>> createBucket(@RequestParam String bucketName) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        List<Bucket> buckets = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withClientConfiguration(clientConfig)
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(endPoint, Region.CN_Beijing.name()))
                    .withPathStyleAccessEnabled(true).build();
            CreateBucketRequest bucketRequest = new CreateBucketRequest(bucketName);
            bucketRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
            s3Client.createBucket(bucketRequest);

            buckets = s3Client.listBuckets();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return ResponseEntity.ok(buckets);
    }

    @GetMapping("/checkExist")
    public ResponseEntity<Boolean> checkExist(@RequestParam String bucketName) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);
        boolean exists = false;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withClientConfiguration(clientConfig)
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, ""))
                    .withPathStyleAccessEnabled(true).build();

            exists = s3Client.doesBucketExist(bucketName);
            log.info("桶是否存在:{}", s3Client.doesBucketExist(bucketName));
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/queryAllObjects")
    public ResponseEntity queryAllObjects(@RequestParam String bucketName) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);

//		String bucketName = "md-13090440321";
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withClientConfiguration(clientConfig)
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(endPoint, Region.CN_Beijing.name()))
                    .withPathStyleAccessEnabled(true).build();

            ListObjectsV2Result result = s3Client.listObjectsV2(bucketName);
            List<S3ObjectSummary> objects = result.getObjectSummaries();
            for (S3ObjectSummary os : objects) {
                log.info("os {}", os);
                log.info("* {}", os.getKey());
            }

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/generateUrl")
    public ResponseEntity generateUrl(@RequestParam String bucketName, @RequestParam String key) {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);

        // String bucketName = "cxyjy";
        URL url = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withClientConfiguration(clientConfig)
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(endPoint, Region.CN_Beijing.name()))
                    .withPathStyleAccessEnabled(true).build();

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);

            generatePresignedUrlRequest.setExpiration(null);
//			s3Client.gen
            url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);// 生成URL
            log.info("下载地址:{}", url);// 可以用这个url来下载文件

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return ResponseEntity.ok(url);
    }

    @GetMapping("/getPolicyText")
    public ResponseEntity getPolicyText(@RequestParam String bucketName) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);

        String policyText = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withClientConfiguration(clientConfig)
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(endPoint, Region.CN_Beijing.name()))
                    .withPathStyleAccessEnabled(true).build();

            // SetBucketPolicyRequest setBucketPolicyRequest = new SetBucketPolicyRequest();

            // s3Client.setBucketPolicy();
            BucketPolicy bucketPolicy = s3Client.getBucketPolicy(bucketName);
            policyText = bucketPolicy.getPolicyText();

            if (policyText == null) {
                System.out.println("The specified bucket has no bucket policy.");
            } else {
                System.out.println("Returned policy:");
                System.out.println("----");
                System.out.println(policyText);
                System.out.println("----\n");
            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return ResponseEntity.ok(policyText);

    }

    @GetMapping("/getBucketAcl")
    public ResponseEntity getBucketAcl(@RequestParam String bucketName) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        List<Grant> grants = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withClientConfiguration(clientConfig)
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(endPoint, Region.CN_Beijing.name()))
                    .withPathStyleAccessEnabled(true).build();

            AccessControlList acl = s3Client.getBucketAcl(bucketName);
            grants = acl.getGrantsAsList();
            for (Grant grant : grants) {
                log.info("  {}: {}", grant.getGrantee().getIdentifier(), grant.getPermission().toString());
            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        return ResponseEntity.ok(grants);
    }


    @GetMapping("/queryAllObjectsByConfig")
    public ResponseEntity queryAllObjectsByConfig() {
        String bucketName = "md-13090440321";
        return ResponseEntity.ok(oosClient.queryAllObjects(bucketName));
    }

    @GetMapping("/getObjectURL")
    public ResponseEntity<String> getObjectURL(@RequestParam String key) {
        return ResponseEntity.ok(oosClient.getObjectUrl(key));
    }

    @ApiOperation(value = "获取文件内容基于Apache-VFS")
    @GetMapping("/getFile")
    public ResponseEntity<Object> readVfsFile(){
        Resource resource = resourceLoader.getResource("vfs://cccccc/ttttt.text");
        ApacheVfsResource apacheVfsResource = (ApacheVfsResource)resourceLoader.getResource("vfs://cccccc/ttttt.text");

        List lines = null;
        try {
            File file = apacheVfsResource.getFile();
            lines = FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(lines);
    }


}
