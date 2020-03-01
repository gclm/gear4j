# chaos 工具

## 功能介绍

- id 生成
- IP 工具
- 文件处理
- 通用异常处理
- 二维码生成/解析
- SpringServer 服务端口号和上下文路径获取
- 通用 Response 封装，支持 Result 和 Page

## 使用教程

### 引入Jar
- maven
```xml
<dependency>
    <groupId>club.gclmit</groupId>
    <artifactId>chaos</artifactId>
    <version>0.0.6</version>
</dependency>
```
- gradle
```
implementation 'club.gclmit:chaos:0.0.6'
```


### 二维码生成/解析

详细代码：[QRCodeHelperTest.java](src/test/java/club/gclmit/chaos/QRCodeHelperTest.java)

```java
public static void generateQrCodeTest() throws IOException, WriterException {
    QRCode qrCode = QRCode.getInstance();
    qrCode.setContent("https://blog.gclmit.club/");
    qrCode.setType(QRCodeConfig.IMAGE_TYPE_FILE);
    System.out.println(QRCodeHelper.generateQRCode(qrCode));
}

public static void parseQRCodeByURLTest() throws IOException, NotFoundException {
    URL url = new URL("https://gitee.com/gclm/img/raw/master/20190814143308-4LGn2F.jpg");
    System.out.println(QRCodeHelper.parseQRCode(url));
}


public static void parseQRCodeByFileTest() throws IOException, NotFoundException {

    String path = System.getProperty("user.dir");
    String filePath = new StringBuilder(path).append("//src//test//resources//").toString();

    System.out.println(QRCodeHelper.parseQRCode(new File(filePath,"alipay.jpg")));
    System.out.println(QRCodeHelper.parseQRCode(filePath+"alipay.jpg"));
}
```

### 文件处理

详细代码：[FileHelperTest.java](src/test/java/club/gclmit/chaos/FileHelperTest.java)

```java
private static void getFileTypeTest() throws IOException {
    String path = System.getProperty("user.dir");
    String filePath = new StringBuilder(path).append("//src//test//resources//").toString();

    System.out.println(FileHelper.getFileType(filePath + "test.png").getKey());
    System.out.println(FileHelper.getFileType(filePath + "test.pdf").getKey());
}
```

### id 生成

详细代码： [IDHelperTest.java](src/test/java/club/gclmit/chaos/IDHelperTest.java)

```java
public static void main(String[] args) {
    System.out.println(IDHelper.getLongId());
    System.out.println(IDHelper.getStringId());
}
```


### Response 处理

- 通用的 RestAPI 请求结构返回封装
- swagger 常见请求封装

### IP 工具



## 鸣谢
> 感谢 JetBrains 提供的非商业开源软件开发授权
> Thanks for non-commercial open source development authorization by JetBrains
