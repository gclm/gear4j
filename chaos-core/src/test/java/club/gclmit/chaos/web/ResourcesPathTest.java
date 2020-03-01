package club.gclmit.chaos.web;

import club.gclmit.chaos.core.helper.file.ResourcesPath;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/30 20:09
 * @version: V1.0
 * @since 1.8
 */
public class ResourcesPathTest {

    public static void main(String[] args) throws IOException {
        ResourcesPath resourcesPath = new ResourcesPath();

        BufferedReader reader = resourcesPath.bufferRead("content-type.properties");


        System.out.println(resourcesPath.getPath("content-type.properties"));
        System.out.println(resourcesPath.getPath());
    }
}
