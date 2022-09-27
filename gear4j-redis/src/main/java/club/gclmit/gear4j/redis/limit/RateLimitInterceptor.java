package club.gclmit.gear4j.redis.limit;

import club.gclmit.gear4j.core.exception.Gear4jException;
import club.gclmit.gear4j.core.utils.ServletUtils;
import club.gclmit.gear4j.redis.cache.RedisCache;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Redis 的限流拦截器
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/17 16:34
 * @since jdk11
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

	@Lazy
	@Resource
	private RedisCache redisCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            RateLimit currentLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
            // 存在注解
            if (currentLimit != null) {
                // 限流条件
                int size = currentLimit.size();
                long time = currentLimit.time();

                // 开启限流
                if (time > 0 && size > 0) {
                    // 创建主键
                    String key = request.getContextPath() + ":" + request.getServletPath() + ":"
                        + ServletUtils.getClientIp(request);

                    // 初次访问
                    if (!redisCache.hasKey(key)) {
                        redisCache.cacheValue(key, 1L, time, TimeUnit.SECONDS);
                        return true;
                    }

                    Long redisSize = (Long)redisCache.getValue(key);
                    long redisTime = redisCache.getExpire(key);
                    // 流量溢出
                    if (redisSize >= size) {
                        throw new Gear4jException("当前请求频繁，请稍后重试！");
                    }
                    // 更新缓存
                    redisCache.cacheValue(key, redisSize + 1, redisTime, TimeUnit.SECONDS);
                }
            }
        }
        return true;
    }
}
