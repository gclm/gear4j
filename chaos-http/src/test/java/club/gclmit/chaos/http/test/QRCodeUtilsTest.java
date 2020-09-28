package club.gclmit.chaos.http.test;

import club.gclmit.chaos.http.lang.Barcode;
import club.gclmit.chaos.http.lang.BarcodeImageType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
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
//        generate1();
        generate2();
//        parse();

    }

    public static void generate1() throws IOException, WriterException {
        File logo = new File("/Users/gclm/Pictures/Chaos.png");

        Barcode.Builder builder = Barcode.of().
                content("https://blog.gclmit.club/")
                .size(400, 400);

        String path = builder.qrCode().generate(BarcodeImageType.JPG, new File(System.getProperty("user.dir"), IdUtils.snowflakeId() + ".jpg"));
        System.out.println("默认颜色：" + path);

        builder.color(0xFFFFFFFF,0xFF00A2FF);
        String path1 = builder.qrCode().generate(BarcodeImageType.JPG, new File(System.getProperty("user.dir"), IdUtils.snowflakeId() + ".jpg"));
        System.out.println("指定颜色:" + path1);

        System.out.println("base64在线预览:\ndata:image/jpg;base64," + builder.qrCode().generate(BarcodeImageType.JPG, true));

    }


    public static void generate2() throws IOException, WriterException {
        File logo = new File("/Users/gclm/Pictures/avatar.jpg");

        Barcode.Builder builder = Barcode.of().
                content("https://blog.gclmit.club/")
                .size(400, 400);

        String path = builder.qrCode(logo).generate(BarcodeImageType.JPG, new File(System.getProperty("user.dir"), IdUtils.simpleUUID() + ".jpg"));
        System.out.println("logo: "+ path);
    }

    public static void parse() throws IOException, NotFoundException {

        URL url = new URL("https://raw.githubusercontent.com/gclm/payment-code/master/doc/img/alipay.jpg");
        Result from = Barcode.from(url).decode();
        System.out.println(from.getText());
        System.out.println("==============");
        System.out.println(from.toString());
    }
}
