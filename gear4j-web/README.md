# gear4j-web

## 基本介绍

- 扩展
  1. 基于MyBatis Plus 提供默认Restful API
  2. 封装通用Query请求和统一的ApiResult返回
  3. 基于jsoup、压缩提供简单waf支持
  4. 基于自定义版本注解提供版本支持
  5. 提供validate、chaos组件的统一异常处理
- 增强
  1. 提供json序列化支持、解决js long精度丢失问题
  2. 提供跨域支持

## 使用方式

### 添加依赖

#### maven

```xml

<dependency>
    <groupId>club.gclmit</groupId>
    <artifactId>gear4j-web</artifactId>
    <version>${version}</version>
</dependency>
```

#### gradle

```groovy
compile("club.gclmit:gear4j-web:${version}")
```

### 相关代码

### 新增ChaosConfig获取gear4j-web增强支持

```java
import EnableChaos;
import org.springframework.context.annotation.Configuration;

/**
 * Chaos 配置
 *
 * @author gclm
 */
@Configuration
@EnableChaos
public class ChaosConfig {
}
```
### 其余扩展使用，请看官方示例[摸鱼导航](https://github.com/DandelionAdmin/mess-fish)
