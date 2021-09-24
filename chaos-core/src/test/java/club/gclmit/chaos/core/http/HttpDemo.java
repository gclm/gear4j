package club.gclmit.chaos.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 测试 jdk11 httpclient
 *
 * @author gclm
 * @version Version 1.0.0
 * @since 2021/9/15 6:11 下午
 * @since 11
 */
public class HttpDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://i.loli.net/2021/09/15/WCBXapRJ6lFjTZP.jpg"))
                .header("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36")
                .GET().build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        System.out.println(response.statusCode());
        System.out.println(response.body().available());
    }
}
