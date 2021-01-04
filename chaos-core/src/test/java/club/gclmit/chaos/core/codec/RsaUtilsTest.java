package club.gclmit.chaos.core.codec;

/**
 * RSAUtils
 *
 * @author gclm
 * @since 1/4/2021 4:03 PM
 * @since 1.8
 */
public class RsaUtilsTest {

    private static final String src = "12341321212abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) throws Exception {
        RsaUtils.RsaKeyPair keyPair = RsaUtils.generateKeyPair();
        System.out.println();
        System.out.println("公钥：" + keyPair.getPublicKey());
        System.out.println("私钥：" + keyPair.getPrivateKey());
        System.out.println();
        test1(keyPair, src);
        System.out.println();
        test2(keyPair, src);
        System.out.println();
        test3(keyPair,src);
    }

    public static void test3(RsaUtils.RsaKeyPair keyPair, String source) throws Exception {
        System.out.println("***************** 签名验证 *****************");

//        String text1 = RsaUtils.encodeByPublicKey(keyPair.getPublicKey(), source);
//        boolean verify = RsaUtils.verify(source, text1, keyPair.getPublicKey());
//        String encodeByPrivateKey = RsaUtils.encodeByPrivateKey(keyPair.getPrivateKey(), source);
//        boolean verify1 = RsaUtils.verify(source, encodeByPrivateKey, keyPair.getPublicKey());
//
//        System.out.println("公钥加密公钥验证：" + verify);
//        System.out.println("私钥加密公钥验证：" + verify1);
        System.out.println("***************** 签名验证 *****************");
    }


    /**
     * 公钥加密私钥解密
     */
    private static void test1(RsaUtils.RsaKeyPair keyPair, String source) throws Exception {
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
    private static void test2(RsaUtils.RsaKeyPair keyPair, String source) throws Exception {
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
