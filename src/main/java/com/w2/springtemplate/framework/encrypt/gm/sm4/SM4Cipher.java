package com.w2.springtemplate.framework.encrypt.gm.sm4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.EnumMap;
import java.util.Map;

public class SM4Cipher {
	private final Map<SM4Mode, Cipher> cipherMap = new EnumMap<>(SM4Mode.class);

	public SM4Cipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
		for (SM4Mode mode : SM4Mode.values()) {
			Cipher cipher = Cipher.getInstance(mode.getName(), BouncyCastleProvider.PROVIDER_NAME);
			cipherMap.put(mode, cipher);
		}
	}

	public Cipher getCipher(SM4Mode SM4Mode) {
		return cipherMap.get(SM4Mode);
	}
}
