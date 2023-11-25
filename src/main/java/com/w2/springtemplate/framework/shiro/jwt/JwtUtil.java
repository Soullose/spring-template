package com.w2.springtemplate.framework.shiro.jwt;

import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

	private static final String SECRET_KET = "502B614D71315565304F482B414D64526151665144326535656A6778313372537941543063655A734B30633D";

	// 有效期
	public static final Long JWT_TTL = 60 * 60 * 1000L; // 60 * 60 * 1000l一个小时
	// 设置密码明文
	public static final String JWT_KEY = "OPEN";

	private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

	/**
	 * 生成jtw(uuid)
	 *
	 * @param subject
	 *            token中要存放的数据（json格式）
	 * @return
	 */
	public static String createJWT(String subject, Map<String, Object> claims) {
		JwtBuilder builder = getJwtBuilder(subject, claims, null, getUUID());// 设置过期时间
		return builder.compact();
	}

	/**
	 * 生成jtw(uuid)
	 *
	 * @param subject
	 *            token中要存放的数据（json格式）
	 * @param ttlMillis
	 *            token超时时间
	 * @return
	 */
	public static String createJWT(String subject, Map<String, Object> claims, Long ttlMillis) {
		JwtBuilder builder = getJwtBuilder(subject, claims, ttlMillis, getUUID());// 设置过期时间
		return builder.compact();
	}

	/**
	 * 创建token
	 *
	 * @param id
	 * @param subject
	 * @param ttlMillis
	 * @return
	 */
	public static String createJWT(String subject, Map<String, Object> claims, Long ttlMillis, String id) {
		JwtBuilder builder = getJwtBuilder(subject, claims, ttlMillis, id);// 设置过期时间
		return builder.compact();
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 生成加密后的秘钥 secretKey
	 *
	 * @return
	 */
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.getDecoder().decode(SECRET_KET);
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
	}

	/**
	 * 解析
	 *
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
	}

	private static JwtBuilder getJwtBuilder(String subject, Map<String, Object> claims, Long ttlMillis, String uuid) {
		SecretKey secretKey = generalKey();
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		if (ttlMillis == null) {
			ttlMillis = JwtUtil.JWT_TTL;
		}
		long expMillis = nowMillis + ttlMillis;
		Date expDate = new Date(expMillis);
		return Jwts.builder().id(uuid)// 唯一的ID
				.header().empty().add(ImmutableMap.of("typ", "JWT", "alg", "HS256")).and().claims(claims)
				.subject(subject) // 主题 可以是JSON数据
				.issuer("w2") // 签发者
				.issuedAt(now) // 签发时间
				.signWith(secretKey, ALGORITHM) // 使用HS256对称加密算法签名, 第二个参数为秘钥
				.expiration(expDate);
	}
}
