## chaos-bom

> `chaos-bom`，主要用来集中管理项目依赖的版本，更加灵活地维护所有依赖的版本信息。

### Maven

```xml

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>club.gclmit</groupId>
            <artifactId>chaos-bom</artifactId>
            <version>${chaosVersion}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Gradle

Spring boot 环境中可以开启 `apply plugin: "io.spring.dependency-management"` 插件。

```groovy
dependencyManagement {
    imports {
        mavenBom "club.gclmit:chaos-bom:${chaosVersion}"
    }
}
```

## 使用 snapshots

`注意`：`snapshots` 版本会及时响应，修复最新的 `bug` 或者必要的需求。

### maven

```xml

<repositories>
    <repository>
        <id>sonatype-nexus-snapshots</id>
        <name>Sonatype Nexus Snapshots</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
        <releases>
            <enabled>false</enabled>
        </releases>
    </repository>
</repositories>
```

### gradle

```groovy
repositories {
    mavenLocal()
    maven { url "https://maven.aliyun.com/repository/public" }
    maven { url "https://maven.aliyun.com/repository/spring" }
    maven { url "https://maven.aliyun.com/repository/spring-plugin" }
    maven { url "https://repo.spring.io/libs-release" }
    maven { url "https://repo.spring.io/milestone" }
    // 添加 snapshots 库地址
    maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    mavenCentral()
}
```