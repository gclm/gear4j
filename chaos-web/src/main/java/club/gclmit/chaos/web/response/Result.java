package club.gclmit.chaos.web.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据封装
 *
 * @author: gclm
 * @see 1.0
 */
@ApiModel(value = "通用消息响应", description = "通用消息响应")
public class Result {

    private static String TIMESTAMP =  String.valueOf(Clock.systemDefaultZone().millis());

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码", required = true)
    private Integer code;

    /**
     * 响应提示
     */
    @ApiModelProperty(value = "响应提示消息", required = true)
    private String message;

    /**
     * 响应时间戳
     */
    @ApiModelProperty(value = "响应时间戳", required = true)
    private String timestamp = TIMESTAMP;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据", required = false)
    private Object data;

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result result(boolean flag) {
        if (flag) {
            return ok();
        }
        return fail("");
    }

    public static Result result(StatusCode statusCode, Object data) {
        return result(statusCode, null, data);
    }

    public static Result result(StatusCode statusCode, String msg, Object data) {
        String message = statusCode.getMessage();
        if (StringUtils.isNotBlank(msg)) {
            message = msg;
        }
        return new Result(statusCode.getCode(), message, data);
    }

    public static Result ok() {
        return ok(null);
    }

    public static Result ok(Object data) {
        return result(StatusCode.SUCCESS, data);
    }

    public static Result ok(String message,Object data) {
        return result(StatusCode.SUCCESS, message, data);
    }

    public static Result ok(Integer code, String message,Object data) {
        return new Result(code, message, data);
    }

    public static Result okMap(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return ok(map);
    }

    public static Result fail() {
        return fail(StatusCode.FAIL);
    }

    public static Result fail(String message) {
        return result(StatusCode.FAIL, message, null);
    }

    public static Result fail(StatusCode statusCode) {
        return result(statusCode, null);
    }

    public static Result fail(StatusCode statusCode, Object data) {
        if (StatusCode.SUCCESS == statusCode) {
            throw new RuntimeException("失败结果状态码不能为：" + statusCode.getCode());
        }
        return result(statusCode, data);
    }

    public static Result fail(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static Result fail(String message,Object data) {
        return new Result(StatusCode.FAIL.getCode(), message, data);
    }

    public static Result fail(Integer code, String message,Object data) {
        return new Result(code, message, data);
    }

    public static Result failMap(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return result(StatusCode.FAIL, map);
    }

    public static String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public static void setTIMESTAMP(String TIMESTAMP) {
        Result.TIMESTAMP = TIMESTAMP;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
