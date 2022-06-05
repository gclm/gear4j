# chaos-logger

## 基本介绍

基于Filter实现的请求日志记录方案

## 使用方式

### 添加依赖

#### maven

```xml

<dependency>
    <groupId>club.gclmit</groupId>
    <artifactId>chaos-logger</artifactId>
    <version>${version}</version>
</dependency>
```

#### gradle

```groovy
compile("club.gclmit:chaos-logger:${version}")
```

> logger 模块不建议单独引用使用，请配合chaos-spring-boot-starter 模块使用
