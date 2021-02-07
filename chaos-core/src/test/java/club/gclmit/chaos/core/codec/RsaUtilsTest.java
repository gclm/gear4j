package club.gclmit.chaos.core.codec;

import org.junit.Test;

/**
 * RSAUtils
 *
 * @author gclm
 * @since 1/4/2021 4:03 PM
 * @since 1.8
 */
public class RsaUtilsTest {

    private static final String SRC = "12341321212abcdefghijklmnopqrstuvwxyz";

    @Test
    public static void test3() throws Exception {
        RsaUtils.RsaKeyPair keyPair = RsaUtils.generateKeyPair();
        String source = SRC;

//        System.out.println("***************** 签名验证 *****************");

//        String text1 = RsaUtils.encodeByPublicKey(keyPair.getPublicKey(), source);
//        boolean verify = RsaUtils.verify(source, text1, keyPair.getPublicKey());
//        String encodeByPrivateKey = RsaUtils.encodeByPrivateKey(keyPair.getPrivateKey(), source);
//        boolean verify1 = RsaUtils.verify(source, encodeByPrivateKey, keyPair.getPublicKey());
//
//        System.out.println("公钥加密公钥验证：" + verify);
//        System.out.println("私钥加密公钥验证：" + verify1);
//        System.out.println("***************** 签名验证 *****************");
    }


    /**
     * 公钥加密私钥解密
     */
    @Test
    public void test1() throws Exception {
        RsaUtils.RsaKeyPair keyPair = RsaUtils.generateKeyPair();
        String source = SRC;

        System.out.println("***************** 公钥加密私钥解密开始 *****************");
        String text1 = RsaUtils.encodeByPublicKey(keyPair.getPublicKey(), source);
        String text2 = RsaUtils.decodeByPrivateKey(keyPair.getPrivateKey(), text1);
        System.out.println("加密前：" + source);
        System.out.println("加密后：" + text1);
        System.out.println("解密后：" + text2);
        if (source.equals(text2)) {
            System.out.println("解密字符串和原始字符串一致，解密成功");
        } else {
            System.out.println("解密字符串和原始字符串不一致，解密失败");
        }
        System.out.println("***************** 公钥加密私钥解密结束 *****************");
    }

    /**
     * 私钥加密公钥解密
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        RsaUtils.RsaKeyPair keyPair = RsaUtils.generateKeyPair();
        String source = SRC;

        System.out.println("***************** 私钥加密公钥解密开始 *****************");
        String text1 = RsaUtils.encodeByPrivateKey(keyPair.getPrivateKey(), source);
        String text2 = RsaUtils.decodeByPublicKey(keyPair.getPublicKey(), text1);
        System.out.println("加密前：" + source);
        System.out.println("加密后：" + text1);
        System.out.println("解密后：" + text2);
        if (source.equals(text2)) {
            System.out.println("解密字符串和原始字符串一致，解密成功");
        } else {
            System.out.println("解密字符串和原始字符串不一致，解密失败");
        }
        System.out.println("***************** 私钥加密公钥解密结束 *****************");
    }
}
