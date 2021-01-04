package club.gclmit.chaos.core.codec;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.util.CharsetUtils;
import lombok.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Rsa 加密实现
 * 参考： https://www.jianshu.com/p/048be4864559
 *
 * @author gclm
 * @since 1.8
 */
@Slf4j
@UtilityClass
public class RsaUtils {

    /**
     * 获取RSA算法
     *
     * @return 获取Rsa算法
     */
    public static String getRsaAlgorithm(){
        return Algorithm.RSA.getValue();
    }

    /**
     * 公钥解密
     *
     * @param publicKeyText 公钥
     * @param text          加密的文本
     * @return              解密的文本
     * @throws Exception    解签异常
     */
    public static String decodeByPublicKey(String publicKeyText, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64Utils.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance(getRsaAlgorithm());
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(getRsaAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64Utils.decodeBase64(text));
        return new String(result);
    }


    /**
     * 私钥解密
     *
     * @param privateKeyText  私钥
     * @param text            加密的文本
     * @return                解密的文本
     * @throws Exception      解签异常
     */
    public static String decodeByPrivateKey(String privateKeyText, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64Utils.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance(getRsaAlgorithm());
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance(getRsaAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64Utils.decodeBase64(text));
        return new String(result);
    }

    /**
     * 私钥加密
     *
     * @param privateKeyText 私钥
     * @param text           待加密的文本
     * @return 加密的文本
     * @throws Exception 签名异常
     */
    public static String encodeByPrivateKey(String privateKeyText, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64Utils.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance(getRsaAlgorithm());
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(getRsaAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64Utils.encodeBase64String(result);
    }

    /**
     * 公钥加密
     *
     * @param publicKeyText 公钥
     * @param text 待加密的文本
     * @return 加密的文本
     * @throws Exception 签名异常
     */
    public static String encodeByPublicKey(String publicKeyText, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64Utils.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance(getRsaAlgorithm());
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance(getRsaAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64Utils.encodeBase64String(result);
    }

    /**
     * 构建RSA密钥对
     *
     * @return 秘钥对
     * @throws NoSuchAlgorithmException 签名异常
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64Utils.encodeBase64String(rsaPublicKey.getEncoded());;
        String privateKeyString = Base64Utils.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    /**
     * RSA密钥对对象
     */
    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class RsaKeyPair {
        private String publicKey;
        private String privateKey;
    }
}
