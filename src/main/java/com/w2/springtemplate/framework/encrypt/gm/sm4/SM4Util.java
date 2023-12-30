package com.w2.springtemplate.framework.encrypt.gm.sm4;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class SM4Util {
    private static final String ALGORITHM_NAME = "SM4";
    private static KeyGenerator kg;

    public SM4Util() throws NoSuchProviderException, NoSuchAlgorithmException {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
    }
    /**
     * SM4算法目前只支持128位（即密钥16字节）
     */
    public static final int DEFAULT_KEY_SIZE = 128;

    public byte[] generateKey() {
        kg.init(DEFAULT_KEY_SIZE, new SecureRandom());
        return kg.generateKey().getEncoded();
    }
    /**
     * SM4加密
     *
     * @param cipher                cipher
     * @param input                 明文数据
     * @param sm4Key                SecretKeySpec
     * @param iv                    初始向量(ECB模式下传NULL), IV must be 16 bytes long
     * @return byte[]
     * @throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException
     */
    public byte[] encrypt(Cipher cipher, byte[] input, SecretKeySpec sm4Key, byte[] iv) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        IvParameterSpec ivParameterSpec = null;
        if (iv != null) {
            ivParameterSpec = new IvParameterSpec(iv);
        }
        return sm4(input, sm4Key, cipher, ivParameterSpec, Cipher.ENCRYPT_MODE);
    }

    /**
     * SM4解密
     *
     * @param cipher                cipher
     * @param input                 密文数据
     * @param sm4Key                SecretKeySpec
     * @param iv                    初始向量(ECB模式下传NULL), IV must be 16 bytes long
     * @return byte[]
     * @throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException
     */
    public byte[] decrypt(Cipher cipher, byte[] input, SecretKeySpec sm4Key, byte[] iv) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        IvParameterSpec ivParameterSpec = null;
        if (iv != null) {
            ivParameterSpec = new IvParameterSpec(iv);
        }
        return sm4(input, sm4Key, cipher, ivParameterSpec, Cipher.DECRYPT_MODE);
    }

    /**
     * 执行sm4加解密
     *
     * @param input                 明文或密文，与参数mode有关
     * @param sm4Key                   密钥
     * @param cipher                 chipher
     * @param ivParameterSpec       初始向量(ECB模式下传NULL)
     * @param mode                  1-加密；2-解密
     * @return byte[]
     * @throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException
     */
    private static byte[] sm4(byte[] input, SecretKeySpec sm4Key, Cipher cipher, IvParameterSpec ivParameterSpec, int mode) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (ivParameterSpec == null) {
            cipher.init(mode, sm4Key);
        } else {
            cipher.init(mode, sm4Key, ivParameterSpec);
        }
        return cipher.doFinal(input);
    }




//    public static final String ALGORITHM_NAME = "SM4";
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
    public static final String ALGORITHM_NAME_ECB_NOPADDING = "SM4/ECB/NoPadding";
    public static final String ALGORITHM_NAME_CBC_PADDING = "SM4/CBC/PKCS5Padding";
    public static final String ALGORITHM_NAME_CBC_NOPADDING = "SM4/CBC/NoPadding";

    /**
     * SM4算法目前只支持128位（即密钥16字节）
     */
//    public static final int DEFAULT_KEY_SIZE = 128;

//    public static byte[] generateKey() throws NoSuchAlgorithmException, NoSuchProviderException {
//        return generateKey(DEFAULT_KEY_SIZE);
//    }

    public static byte[] generateKey(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    public static byte[] encrypt_ECB_Padding(byte[] key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = generateECBCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_ECB_Padding(byte[] key, byte[] cipherText)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = generateECBCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_ECB_NoPadding(byte[] key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = generateECBCipher(ALGORITHM_NAME_ECB_NOPADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_ECB_NoPadding(byte[] key, byte[] cipherText)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = generateECBCipher(ALGORITHM_NAME_ECB_NOPADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_CBC_Padding(byte[] key, byte[] iv, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        Cipher cipher = generateCBCCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_CBC_Padding(byte[] key, byte[] iv, byte[] cipherText)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidAlgorithmParameterException {
        Cipher cipher = generateCBCCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_CBC_NoPadding(byte[] key, byte[] iv, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        Cipher cipher = generateCBCCipher(ALGORITHM_NAME_CBC_NOPADDING, Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_CBC_NoPadding(byte[] key, byte[] iv, byte[] cipherText)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidAlgorithmParameterException {
        Cipher cipher = generateCBCCipher(ALGORITHM_NAME_CBC_NOPADDING, Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(cipherText);
    }

    public static byte[] doCMac(byte[] key, byte[] data) throws NoSuchProviderException, NoSuchAlgorithmException,
            InvalidKeyException {
        Key keyObj = new SecretKeySpec(key, ALGORITHM_NAME);
        return doMac("SM4-CMAC", keyObj, data);
    }

    public static byte[] doGMac(byte[] key, byte[] iv, int tagLength, byte[] data) {
        org.bouncycastle.crypto.Mac mac = new GMac(new GCMBlockCipher(new SM4Engine()), tagLength * 8);
        return doMac(mac, key, iv, data);
    }

    /**
     * 默认使用PKCS7Padding/PKCS5Padding填充的CBCMAC
     *
     * @param key
     * @param iv
     * @param data
     * @return
     */
    public static byte[] doCBCMac(byte[] key, byte[] iv, byte[] data) {
        SM4Engine engine = new SM4Engine();
        org.bouncycastle.crypto.Mac mac = new CBCBlockCipherMac(engine, engine.getBlockSize() * 8, new PKCS7Padding());
        return doMac(mac, key, iv, data);
    }

    /**
     * @param key
     * @param iv
     * @param padding 可以传null，传null表示NoPadding，由调用方保证数据必须是BlockSize的整数倍
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] doCBCMac(byte[] key, byte[] iv, BlockCipherPadding padding, byte[] data) throws Exception {
        SM4Engine engine = new SM4Engine();
        if (padding == null) {
            if (data.length % engine.getBlockSize() != 0) {
                throw new Exception("if no padding, data length must be multiple of SM4 BlockSize");
            }
        }
        org.bouncycastle.crypto.Mac mac = new CBCBlockCipherMac(engine, engine.getBlockSize() * 8, padding);
        return doMac(mac, key, iv, data);
    }


    private static byte[] doMac(org.bouncycastle.crypto.Mac mac, byte[] key, byte[] iv, byte[] data) {
        CipherParameters cipherParameters = new KeyParameter(key);
        mac.init(new ParametersWithIV(cipherParameters, iv));
        mac.update(data, 0, data.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    private static byte[] doMac(String algorithmName, Key key, byte[] data) throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        mac.init(key);
        mac.update(data);
        return mac.doFinal();
    }

    private static Cipher generateECBCipher(String algorithmName, int mode, byte[] key)
            throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    private static Cipher generateCBCCipher(String algorithmName, int mode, byte[] key, byte[] iv)
            throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(mode, sm4Key, ivParameterSpec);
        return cipher;
    }

}
