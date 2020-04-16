package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.qrcode.QRCodeConfig;
import club.gclmit.chaos.core.qrcode.QRCodeHelper;
import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * <p>
 * 二维码工具
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/7 0:18
 * @version: V1.0
 */
public class QRCodeUtilsTest {

    public static void main(String[] args) throws IOException, WriterException, NotFoundException {
        generateQrCodeTest();
        parseQRCodeByURLTest();
        parseQRCodeByFileTest();
    }

    public static void generateQrCodeTest() throws IOException, WriterException {
        QRCodeHelper.QRCode qrCode = QRCodeHelper.getInstance();
        qrCode.setContent("https://blog.gclmit.club/");
        qrCode.setType(QRCodeConfig.IMAGE_TYPE_FILE);
        System.out.println(QRCodeHelper.generateQRCode(qrCode));
    }

    
    public static void parseQRCodeByURLTest() throws IOException, NotFoundException {
        URL url = new URL("https://gitee.com/gclm/images/raw/master/20190814143308-4LGn2F.jpg");
        System.out.println(QRCodeHelper.parseQRCode(url));
    }

    
    public static void parseQRCodeByFileTest() throws IOException, NotFoundException {

        String path = System.getProperty("user.dir");
        String filePath = new StringBuilder(path).append("//src//test//resources//").toString();
        Logger.info(LoggerServer.CHAOS_CORE,"当前项目路径:{}\t文件路径:{}",path,filePath);

        File file = new File(filePath, "alipay.jpg");
        System.out.println();
        if (file.exists()) {
            System.out.println("文件已经存储");
        }

        Assert.isTrue(file.exists(),"文件不存在");
        System.out.println(QRCodeHelper.parseQRCode(new File(filePath,"alipay.jpg")));
        System.out.println(QRCodeHelper.parseQRCode(filePath+"alipay.jpg"));
    }
}
