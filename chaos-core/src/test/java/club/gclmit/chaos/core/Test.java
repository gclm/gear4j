package club.gclmit.chaos.core;

import club.gclmit.chaos.core.io.file.MimeType;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/2 1:20 下午
 * @version: V1.0
 * @since 1.8
 */
public class Test {

    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader("/Users/gclm/Downloads/test.txt");
        BufferedReader reader = new BufferedReader(in);
        String line;
        List<MimeType> mimeTypes  = new ArrayList<>();
        while ((line = reader.readLine()) != null){
            String[] strs = StringUtils.splitPreserveAllTokens(line, "\t");
            String template = "%s(\"%s\",\"%s\",\"%s\"),";
            String sufix = strs[0];
            String docType = strs[1];
            String mimeType = strs[2];
            String Case = sufix.replace(".", "").toUpperCase();
            System.out.println(String.format(template,Case,sufix,docType,mimeType));
        }
    }
}
