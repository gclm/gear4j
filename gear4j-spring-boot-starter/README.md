# gear4j-spring-boot-starter

## 基本介绍

1. 封装各个chaos组件,提供springboot自动注入支持
2. 提供Spring Service 服务工具类，增强相关服务信息展示
3. 封装gear4j-storage组件，提供自动去重、快速查询功能

## 使用方式

### 添加依赖

#### maven

```xml

<dependency>
    <groupId>club.gclmit</groupId>
    <artifactId>gear4j-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
```

#### gradle

```groovy
compile("club.gclmit:gear4j-spring-boot-starter:${version}")
```

### 相关代码
具体使用，请看官方示例[摸鱼导航](https://github.com/DandelionAdmin/mess-fish)