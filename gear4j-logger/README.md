# gear4j-logger

## 基本介绍

基于Filter实现的请求日志记录方案

## 使用方式

### 添加依赖

#### maven

```xml

<dependency>
    <groupId>club.gclmit</groupId>
    <artifactId>gear4j-logger</artifactId>
    <version>${version}</version>
</dependency>
```

#### gradle

```groovy
compile("club.gclmit:gear4j-logger:${version}")
```

> logger 模块不建议单独引用使用，请配合gear4j-spring-boot-starter 模块使用
