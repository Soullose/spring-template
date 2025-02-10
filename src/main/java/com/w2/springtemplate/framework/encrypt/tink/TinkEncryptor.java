//package com.w2.springtemplate.framework.encrypt.tink;
//
//import com.google.crypto.tink.*;
//import com.google.crypto.tink.aead.AeadConfig;
//import com.google.crypto.tink.aead.PredefinedAeadParameters;
//
//import java.security.GeneralSecurityException;
//import java.util.Base64;
//
///**
// * @author wsfzj 2025/1/18
// * @version 1.0
// * @description TODO
// */
//public class TinkEncryptor {
//	private final Aead aead;
//	private static final String ENC_PREFIX = "ENC(";
//	private static final String ENC_SUFFIX = ")";
//
//	public TinkEncryptor() throws GeneralSecurityException {
//		AeadConfig.register();
//		KeysetHandle keysetHandle = KeysetHandle.generateNew(PredefinedAeadParameters.AES256_GCM);
//		this.aead = keysetHandle.getPrimitive(RegistryConfiguration.get(),Aead.class);
//	}
//
//	public TinkEncryptor(String masterKey) throws GeneralSecurityException {
//		AeadConfig.register();
//		TinkProtoKeysetFormat
//		KeysetHandle keysetHandle = KeysetHandle.read(
//				BinaryKeysetReader.withBytes(Base64.getDecoder().decode(masterKey)),
//				null  // 不使用密码保护
//		);
//		this.aead = keysetHandle.getPrimitive(RegistryConfiguration.get(),Aead.class);
//	}
//
//	public String encrypt(String value) throws GeneralSecurityException {
//		byte[] encrypted = aead.encrypt(value.getBytes(), null);
//		return Base64.getEncoder().encodeToString(encrypted);
//	}
//
//	public String decrypt(String encryptedValue) throws GeneralSecurityException {
//		byte[] decoded = Base64.getDecoder().decode(encryptedValue);
//		byte[] decrypted = aead.decrypt(decoded, null);
//		return new String(decrypted);
//	}
//
//	public String encryptWithPrefix(String value) throws GeneralSecurityException {
//		return ENC_PREFIX + encrypt(value) + ENC_SUFFIX;
//	}
//}
