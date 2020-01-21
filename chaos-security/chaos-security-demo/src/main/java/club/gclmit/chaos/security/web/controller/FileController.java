package club.gclmit.chaos.security.web.controller;
import java.io.*;

import club.gclmit.chaos.helper.file.FileHelper;
import club.gclmit.chaos.security.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 文件上传服务
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/4 10:22 上午
 * @version: V1.0
 * @since 1.8
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private String folder = "/Volumes/Code/java/chaos-security/chaos-security-demo";

    @PostMapping
    public FileInfo upload(@RequestParam("file") MultipartFile multipartFile)  throws IOException {

        System.out.println("文件名字:" + multipartFile.getName());
        System.out.println("文件默认名字:" + multipartFile.getOriginalFilename());
        System.out.println("文件大小:" + multipartFile.getSize());

        File file = FileHelper.multipartFileToFile(folder,multipartFile);

        return new FileInfo(file.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public  void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("当前文件存储地址： " + folder);

        try (
                InputStream inputStream = new FileInputStream(new File(folder,id+".txt"));
                OutputStream outputStream = response.getOutputStream();
        ){
            //   设置下载的文件类型
           response.setContentType("application/x-download");
            // 设置下载后的文件名
           response.setHeader("Content-Disposition","attachment;filename=test.txt");

           // 刷新输出流
           IOUtils.copy(inputStream,outputStream);
           outputStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
