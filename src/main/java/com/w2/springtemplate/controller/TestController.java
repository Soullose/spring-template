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
import com.w2.springtemplate.framework.encrypt.gm.sm4.SM4Cipher;
import com.w2.springtemplate.framework.encrypt.gm.sm4.SM4CipherPool;
import com.w2.springtemplate.framework.encrypt.gm.sm4.SM4Mode;
import com.w2.springtemplate.framework.encrypt.gm.sm4.SM4Util;
import com.w2.springtemplate.framework.io.fonts.FontUtils;
import com.w2.springtemplate.framework.io.pdf.PdfBoxUtils;
import com.w2.springtemplate.framework.oos.client.OOSClient;
import com.w2.springtemplate.framework.vfs.ApacheVfsResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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
	// @Autowired
	// private AmazonS3 sfS3Client;

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

		// String bucketName = "md-13090440321";
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
			// s3Client.gen
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
	public ResponseEntity<Object> readVfsFile() {
		Resource resource = resourceLoader.getResource("vfs://cccccc/ttttt.text");
		ApacheVfsResource apacheVfsResource = (ApacheVfsResource) resourceLoader.getResource("vfs://cccccc/ttttt.text");

		List lines = null;
		try {
			File file = apacheVfsResource.getFile();
			lines = FileUtils.readLines(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(lines);
	}

	public static final byte[] SRC_DATA_16B = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8};

	@ApiOperation(value = "测试SM4")
	@GetMapping("/testSM4")
	public ResponseEntity testSM4() {
		SM4CipherPool sm4CipherPool = new SM4CipherPool(4);
		SM4Cipher sm4Cipher = null;
		try {
			sm4Cipher = sm4CipherPool.borrowObject();
			Cipher cipher = sm4Cipher.getCipher(SM4Mode.SM4_CBC_PKCS7Padding);

			byte[] key = "1234567890abcdef".getBytes();
			SecretKeySpec sm4Key = new SecretKeySpec(key, SM4Mode.SM4_CBC_PKCS7Padding.getName());
			byte[] iv = "ilovegolangjava.".getBytes();
			SM4Util instance = new SM4Util();
			//解密校验
			String cryptText = "6c24d235d11a8c1428546ab114500225"; //2d529a9f0042384350311823e3bc11fde5cf93a25b035cda82baf56ccf1f306c   8781d981f7ffd6c1a780f8b213f596aa535c8bb6389923f8329f79a1707966e2 6c24d235d11a8c1428546ab114500225

			log.debug("1:{}",Hex.decode(cryptText));
			log.debug("2:{}",ByteUtils.fromHexString(cryptText));
			byte[] b = instance.decrypt(cipher, Hex.decode(cryptText), sm4Key, iv);
			log.debug("I am encrypted by golang SM4.{}", new String(b));

			//加密校验，SM4加密以下明文以供Go SM4进行解密验证
			byte[] msg = "你好!".getBytes();
			byte[] cryptData = instance.encrypt(cipher, msg, sm4Key, iv);
			String cryptStr = Hex.toHexString(cryptData);

			log.debug(cryptStr);

			log.debug("hex:{}",Hex.toHexString(cryptData));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "测试pdfBox")
	@GetMapping(value = "/testPdfBox")
	public ResponseEntity testPdfBox() throws IOException {
		FontUtils fontUtils = new FontUtils(resourceLoader);
		InputStream miFontL3TTF = fontUtils.getMIFontL3TTF();
		File miFontL3TTFFile = fontUtils.getMIFontL3TTFFile();
		InputStream miFontTTF = fontUtils.getMIFontTTF();
		log.debug("{}",miFontL3TTF != null);
		log.debug("{}",miFontL3TTFFile != null);
		TTFParser ttfParser = new TTFParser();
		TrueTypeFont trueTypeFont = ttfParser.parseEmbedded(miFontTTF);
		Integer marginX = 50;
		Integer marginY = 50;
		PDRectangle a4 = PDRectangle.A4;
		PDDocument document = new PDDocument();
		PDType0Font font = PDType0Font.load(document,trueTypeFont,true);
		PDPage pdPage = new PDPage(a4);
		document.addPage(pdPage);
		PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);

		PdfBoxUtils.beginTextSteam(contentStream, 20f, marginX.floatValue(), a4.getHeight()-(2*marginY));
		// 书写信息
		PdfBoxUtils.drawParagraph(contentStream, "物流单摘要", font, 18);
		PdfBoxUtils.createEmptyParagraph(contentStream, 2);

		contentStream.setFont(font, 13);
		PdfBoxUtils.drawParagraph(contentStream, "物流单号：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a02022099");
		PdfBoxUtils.drawParagraph(contentStream, "结算时间段：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0从20200909到20200807");
		PdfBoxUtils.drawParagraph(contentStream, "商品总数量(件)：\u00a0100000");
		PdfBoxUtils.drawParagraph(contentStream, "商品总价格(元)：\u00a0100000000000");
		PdfBoxUtils.drawParagraph(contentStream, "买卖人名称：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0李白");
		PdfBoxUtils.createEmptyParagraph(contentStream, 4);

		PdfBoxUtils.drawParagraph(contentStream, "公司(盖章)：");
		PdfBoxUtils.createEmptyParagraph(contentStream, 2);
		contentStream.showText("日期：");

		PdfBoxUtils.createEmptyParagraph(contentStream, 16);
		contentStream.newLineAtOffset(195, 0);
		PdfBoxUtils.drawParagraph(contentStream, "小熊猫超级防伪码", font, 12);

		PdfBoxUtils.endTextSteam(contentStream);

		// 划线
		PdfBoxUtils.drawLine(contentStream, marginX, 545, PDRectangle.A4.getWidth() - marginX, 545);
		PdfBoxUtils.drawLine(contentStream, marginX, 410, PDRectangle.A4.getWidth() - marginX, 410);

		contentStream.close();
		// 贴图
		document.save(new FileOutputStream(new File("D:\\dev\\IdeaProjects\\spring-template\\test2.pdf")));
		document.close();
		return  ResponseEntity.ok().build();
	}

}
