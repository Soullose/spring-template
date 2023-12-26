package com.w2.springtemplate.framework.encrypt.gm.sm4;

/**
 *  SM4加密模式
 * @author
 * https://github.com/Hyperledger-TWGC/java-gm/blob/master/src/main/java/twgc/gm/sm4/SM4ModeAndPaddingEnum.java
 */
public enum SM4Mode {
    SM4_ECB_NoPadding("SM4/ECB/NoPadding"),
    SM4_ECB_PKCS5Padding("SM4/ECB/PKCS5Padding"),
    SM4_ECB_PKCS7Padding("SM4/ECB/PKCS7Padding"),
    SM4_CBC_NoPadding("SM4/CBC/NoPadding"),
    SM4_CBC_PKCS5Padding("SM4/CBC/PKCS5Padding"),
    SM4_CBC_PKCS7Padding("SM4/CBC/PKCS7Padding"),
    // CFB,OFB,CTR三种模式无需填充(padding)
    SM4_CFB_NoPadding("SM4/CFB/NoPadding"),
    SM4_OFB_NoPadding("SM4/OFB/NoPadding"),
    SM4_CTR_NoPadding("SM4/CTR/NoPadding");

    private final String name;

    SM4Mode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
