package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.lang.Barcode;
import club.gclmit.chaos.core.lang.BarcodeImageType;
import club.gclmit.chaos.core.lang.qrcode.QrCode;
import cn.hutool.core.util.IdUtil;
import com.google.zxing.WriterException;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 * 二维码工具
 * </p>
 *
 * @author gclm
 * @date 2019/11/7 0:18
 */
public class QrCodeTest {

    @Test
    public void parse() throws IOException {
        String filePath = "/Users/gclm/Projects/java/00-base/chaos/chaos-core/src/test/resources/alipay.jpg";
        URL url = new URL("https://raw.githubusercontent.com/gclm/payment-code/master/doc/img/alipay.jpg");
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(filePath);
        Path path = Paths.get(filePath);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(new FileInputStream(filePath), outputStream);

        System.out.println(QrCode.decode().from(url).decode());
        System.out.println(QrCode.decode().from(file).decode());
        System.out.println(QrCode.decode().from(inputStream).decode());
        System.out.println(QrCode.decode().from(path).decode());
        System.out.println(QrCode.decode().from(outputStream.toByteArray()).decode());
    }


    @Test
    public void generate1() throws IOException, WriterException {
        File logo = new File("/Users/gclm/Pictures/Chaos.png");

        Barcode.Builder builder = Barcode.of().
                content("https://blog.gclmit.club/")
                .size(400, 400);

        String path = builder.qrCode().generate(BarcodeImageType.JPG, new File(System.getProperty("user.dir"), IdUtil.fastSimpleUUID() + ".jpg"));
        System.out.println("默认颜色：" + path);

        builder.color(0xFFFFFFFF, 0xFF00A2FF);
        String path1 = builder.qrCode().generate(BarcodeImageType.JPG, new File(System.getProperty("user.dir"), IdUtil.fastSimpleUUID() + ".jpg"));
        System.out.println("指定颜色:" + path1);

        System.out.println("base64在线预览:\ndata:image/jpg;base64," + builder.qrCode().generate(BarcodeImageType.JPG, true));

    }

    @Test
    public void generate2() throws IOException, WriterException {
        File logo = new File("/Users/gclm/Pictures/avatar.jpg");

        Barcode.Builder builder = Barcode.of().
                content("https://blog.gclmit.club/")
                .size(400, 400);

        String path = builder.qrCode(logo).generate(BarcodeImageType.JPG, new File(System.getProperty("user.dir"), IdUtil.fastSimpleUUID() + ".jpg"));
        System.out.println("logo: " + path);
    }


}
