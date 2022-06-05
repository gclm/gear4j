# chaos-extra

## 基本介绍

由于chaos的原则是依赖轻量化，尽量不依赖于其它配置文件，但是很多时候我们需要针对第三方非常棒的库做一些工具类化的支持，因此chaos-extra包主要用于支持第三方库的工具类支持。

## 现阶段扩展

### 通知类

- 钉钉通知（text、link、markdown、ActionCard）

## 使用方式

### 添加依赖

#### maven

```xml

<dependency>
    <groupId>club.gclmit</groupId>
    <artifactId>chaos-extra</artifactId>
    <version>${version}</version>
</dependency>
```

#### gradle

```groovy
compile("club.gclmit:chaos-extra:${version}")
```

### 相关代码

```java
String webhook="https://oapi.dingtalk.com/robot/send?access_token=ba488c121edfcbdd74f29ca0024b95424f75f6cb8feab18b643c";
String secret="SEC5bad10ba63a293ad88f2bc086dcfa63ebf10e6b6ac5885cd0dd3e55";

System.out.println(DingtalkBot.builder().webhook(webhook).secret(secret).text().content("text 测试")
    .mobiles(List.of("17326039618")).send());

System.out.println(DingtalkBot.builder().webhook(webhook).secret(secret).markdown()
    .mobiles(List.of("17326039618")).text("- xx  - yy  - zz").title("Markdown 测试").send());

```


