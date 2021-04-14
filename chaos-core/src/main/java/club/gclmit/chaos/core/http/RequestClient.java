package club.gclmit.chaos.core.http;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.Map;

/**
 * 通用请求客户端
 *
 * @author gclm
 * @since 1.8
 */
@UtilityClass
public class RequestClient {

    /**
     * json 请求
     */
    public static final String JSON_REQUEST_TYPE = "json";
    /**
     * from 请求
     */
    public static final String FROM_REQUEST_TYPE = "from";
    /**
     * 文件上传请求
     */
    public static final String UPLOAD_REQUEST_TYPE = "multipart/form";

    /**
     * get 请求
     *
     * @param url 请求url
     * @return java.lang.String
     * @author gclm
     */
    public static String get(String url) {
        return get(url, HttpUtils.buildRequestHeader());
    }

    /**
     * get 请求
     *
     * @param url     请求url
     * @param headers 请求头
     * @return java.lang.String
     * @author gclm
     */
    public static String get(String url, Map<String, String> headers) {
        return HttpUtils.buildHttp().async(url).addHeader(headers).get().getResult().getBody().cache().toString();
    }

    /**
     * post 请求
     *
     * @param url    请求url
     * @param params 请求参数
     * @param type   格式类型 json|from
     * @return java.lang.String
     * @author gclm
     */
    public static String post(String url, Map<String, ?> params, String type) {
        return post(url, params, HttpUtils.buildRequestHeader(), type);
    }

    /**
     * post 请求
     *
     * @param url     请求url
     * @param params  请求参数
     * @param headers 请求头
     * @param type    格式类型 json|from
     * @return java.lang.String
     * @author gclm
     */
    public static String post(String url, Map<String, ?> params, Map<String, String> headers, String type) {
        if (JSON_REQUEST_TYPE.equals(type) || FROM_REQUEST_TYPE.equals(type)) {
            return HttpUtils.buildHttp().async(url).addHeader(headers).addBodyPara(params).bodyType(type).post().getResult().getBody().cache().toString();
        }
        return null;
    }

    /**
     * put 请求
     *
     * @param url    请求url
     * @param params 请求参数
     * @return java.lang.String
     * @author gclm
     */
    public static String put(String url, Map<String, ?> params) {
        return put(url, params, HttpUtils.buildRequestHeader());
    }


    /**
     * put 请求
     *
     * @param url     请求url
     * @param params  请求参数
     * @param headers 请求头
     * @return java.lang.String
     * @author gclm
     */
    public static String put(String url, Map<String, ?> params, Map<String, String> headers) {
        return HttpUtils.buildHttp().async(url).addHeader(headers).addBodyPara(params).put().getResult().getBody().cache().toString();
    }

    /**
     * delete 请求
     *
     * @param url 请求url
     * @return java.lang.String
     * @author gclm
     */
    public static String delete(String url) {
        return delete(url, HttpUtils.buildRequestHeader());
    }

    /**
     * delete 请求
     *
     * @param url     请求url
     * @param headers 请求头
     * @return java.lang.String
     * @author gclm
     */
    public static String delete(String url, Map<String, String> headers) {
        return HttpUtils.buildHttp().async(url).addHeader(headers).delete().getResult().getBody().cache().toString();
    }

    /**
     * 上传 请求
     *
     * @param url     请求url
     * @param params  请求参数
     * @param headers 请求头
     * @param fileParam  上传文件参数
     * @param file  上传文件
     * @return java.lang.String
     * @author gclm
     */
    public static String upload(String url, Map<String, ?> params, Map<String, String> headers, String fileParam, File file) {
        return HttpUtils.buildHttp().async(url).addBodyPara(params).addFilePara(fileParam,file).post().getResult().getBody().cache().toString();
    }
}
