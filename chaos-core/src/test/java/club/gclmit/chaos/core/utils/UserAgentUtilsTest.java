package club.gclmit.chaos.core.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserAgent 测试工具类
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @version Version 1.0.0
 * @since 2021/9/26 2:46 下午
 * @since jdk11
 */
public class UserAgentUtilsTest {

	@Test
	public void getUserAgentTest() {
		Map<String, List<String>> userAgents = new HashMap<>();
		String agent = ResourceUtil.readUtf8Str("userAgent.json");
		JSONObject jsonObject = JSONObject.parseObject(agent);
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			String key = String.valueOf(entry.getKey());
			List<String> object = JSONArray.parseArray(jsonObject.getString(key), String.class);
			userAgents.put(key, object);
		}

		for (String str : userAgents.get("chrome")) {
			System.out.println(str);
		}

//        String[] chromes = map.get("chrome");
//        System.out.println(chromes[RandomUtil.randomInt(chromes.length)]);


//    static {
//        try {
//            String userAgent = ResourceUtil.readUtf8Str("userAgent.json");
//            JSONObject jsonObject = JSONObject.parseObject(userAgent);
//            for (int i = 0; i < jsonObject.size(); i++) {
//
//            }
//        } catch (Exception ignored) {
//        }
//    }
	}
}
