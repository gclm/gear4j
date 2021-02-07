package club.gclmit.chaos.core.codec;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 签名算法
 *
 * @author gclm
 * @since 1/4/2021 4:05 PM
 * @since 1.8
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum Algorithm {

    //=======================================================
    // 摘要算法

    /**
     * MD2
     */
    MD2("MD2"),

    /**
     * MD5
     */
    MD5("MD5"),

    /**
     * SHA-1
     */
    SHA1("SHA-1"),

    /**
     * SHA-256
     */
    SHA256("SHA-256"),

    /**
     * SHA-384
     */
    SHA384("SHA-384"),

    /**
     * SHA-512
     */
    SHA512("SHA-512"),

    //======================================================
    // Hmac 加密

    HMAC_MD5("HmacMD5"),



    //=======================================================
    // 非对称加密

    /**
     *RSA
     */
    RSA("RSA");



    private String value;
}
