package club.gclmit.chaos.web;

import club.gclmit.chaos.core.helper.code.QRCodeConfig;
import club.gclmit.chaos.core.helper.code.QRCode;
import club.gclmit.chaos.core.helper.code.QRCodeHelper;
import club.gclmit.chaos.core.helper.logger.Logger;
import club.gclmit.chaos.core.helper.logger.LoggerServer;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.springframework.util.Assert;

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
public class QRCodeHelperTest {

    public static void main(String[] args) throws IOException, WriterException, NotFoundException {

        generateQrCodeTest();
        parseQRCodeByURLTest();
        parseQRCodeByFileTest();
        
    }

    public static void generateQrCodeTest() throws IOException, WriterException {
        QRCode qrCode = QRCode.getInstance();
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
        Logger.info(LoggerServer.File,"当前项目路径:{}\t文件路径:{}",path,filePath);

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
