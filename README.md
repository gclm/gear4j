<p align="center">
	<a href="https://github.com/gclm/chaos"><img src="https://cdn.jsdelivr.net/gh/gclm/images@master/20200414/1586827646660.png"></a>
</p>
<p align="center">
	<strong>Where dreams originate</strong>
</p>
<p align="center">
	<a target="_blank" href="https://search.maven.org/search?q=g:%20club.gclmit%20AND%20a:%20chaos-spring-boot-starter">
        <img src="https://img.shields.io/maven-central/v/club.gclmit/chaos-spring-boot-starter.svg?label=Maven%20Central" />
	</a>
	<a target="_blank" href="http://license.coscl.org.cn/MulanPSL2/">
        <img src="https://img.shields.io/:license-MulanPSL2-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-8+-green.svg" />
	</a>
    <a href="https://codecov.io/gh/gclm/chaos">
        <img src="https://codecov.io/gh/gclm/chaos/branch/master/graph/badge.svg" />
    </a>
    <a href="https://travis-ci.com/gclm/chaos">
        <img src="https://travis-ci.com/gclm/chaos.svg?branch=master" />
    </a>
</p>
<p align="center">
    QQ群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=4684b1c1194706adcc4ce7c9428935d31c0b2a86b51e96cb807fa30f94cebfde">641633175</a>
</p>

##  项目简介
> 一个功能强悍的 Spring Boot Starter 组件

### 仓库地址

- [Github](https://github.com/gclm/chaos.git)
- [Gitee](https://gitee.com/gclm/chaos)
> Gitee 是同步的镜像仓库，有问题请使用 Github Issue 反馈

### 主要特性

- 集成spring boot 常用开发组件集、公共配置、接口请求日志等
- Maven多模块架构
- 集成mybatis plus快速dao操作，单表操作一键配置
- 集成Knife4j，可自动生成api文档
- 使用assembly maven插件进行不同环境打包部署,包含启动、重启命令，配置文件提取到外部config目录

### 技术选型
技术 | 版本 |  备注
-|-|-
Spring Boot | 2.2.1.RELEASE | 最新发布稳定版 
Spring Framework | 5.2.0.RELEASE | 最新发布稳定版 
 Mybatis             | 3.5.3         | 持久层框架              
 Mybatis Plus        | 3.3.1         | mybatis增强框架         
 HikariCP            | 3.4.2         | 数据源                  
 Knife4j             | 2.0.2         | api文档生成工具         
 jasypt              | 3.0.2         | 加密组件                
 BeanUtils           | 1.0.11        | 对象属性复制工具        
 mapstruct           | 1.3.1.Final   | 对象属性复制工具        
 Fastjson            | 1.2.67        | JSON处理工具集          
 reflections         | 0.9.9         | 反射工具包 
 hibernate-validator | 6.0.18.Final  | 后台参数校验注解 
 slf4j               | 1.7.30        | 日志组件                
 logback             | 1.2.3         | 日志组件                
 zxing               | 3.4.0         | 二维码工具              
 lombok              | 1.18.12       | 注解生成Java Bean等工具                    

### 项目结构

```text
.
├── LICENSE
├── README.md                       说明
├── chaos-spring-boot-starter       Spring Boot Starter     
├── chaos-core                      核心模块
├── chaos-logger                    日志模块
├── chaos-storage                   存储模块
├── chaos-annotations               注解模块
└── chaos-web                       web模块
```

## 使用教程

### 添加依赖

**Maven**
```xml
<dependency>
  <groupId>club.gclmit</groupId>
  <artifactId>chaos-spring-boot-starter</artifactId>
  <version>1.4.10.RELEASE</version>
</dependency>
```

**Gradle**
```text
implementation 'club.gclmit:chaos-spring-boot-starter:1.4.10.RELEASE'
```

### 导入SQL
```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chaos_file_info
-- ----------------------------
DROP TABLE IF EXISTS `chaos_file_info`;
CREATE TABLE `chaos_file_info` (
  `id` bigint(22) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '文件名',
  `content_type` varchar(50) NOT NULL DEFAULT '' COMMENT '文件类型',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
  `size` bigint(22) NOT NULL DEFAULT -1 COMMENT '文件大小',
  `md5` varchar(50) NOT NULL DEFAULT '' COMMENT 'md5',
  `e_tag` varchar(50) NOT NULL DEFAULT '' COMMENT 'ETag',
  `oss_key` varchar(50) NOT NULL DEFAULT '' COMMENT 'OSS key',
  `status` int(11) NOT NULL DEFAULT -1 COMMENT '文件状态',
  `upload_time` bigint(16) DEFAULT NULL COMMENT '上传时间',
  `oss_type` int(5) DEFAULT NULL COMMENT '对象存储类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='chaos_file_info';

-- ----------------------------
-- Table structure for chaos_trace_info
-- ----------------------------
DROP TABLE IF EXISTS `chaos_trace_info`;
CREATE TABLE `chaos_trace_info` (
  `id` bigint(22) NOT NULL COMMENT '主键 编号',
  `client_ip` varchar(50) NOT NULL DEFAULT '' COMMENT '客户端请求ip',
  `uri` varchar(100) NOT NULL DEFAULT '' COMMENT '客户端请求的路径',
  `content_type` varchar(100) NOT NULL DEFAULT '' COMMENT '客户端请求方式',
  `session_id` varchar(50) DEFAULT NULL COMMENT 'session ID',
  `method` varchar(50) NOT NULL DEFAULT '' COMMENT '请求方法类型: restful 风格',
  `request_time` bigint(15) NOT NULL DEFAULT -1 COMMENT '请求时间戳（秒）',
  `response_time` bigint(15) NOT NULL DEFAULT -1 COMMENT '接口返回时间',
  `request_body` text DEFAULT '' COMMENT 'requestBody',
  `response_body` text DEFAULT '' COMMENT 'responseBody',
  `request_header` text NOT NULL DEFAULT '' COMMENT 'request 请求头',
  `response_header` text NOT NULL DEFAULT '' COMMENT 'response 响应头',
  `user_agent` varchar(255) NOT NULL DEFAULT '' COMMENT '用户代理',
  `http_code` int(10) DEFAULT NULL COMMENT '状态码',
  `consuming_time` bigint(15) DEFAULT NULL COMMENT '请求耗时（秒）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='chaos_trace_info';

SET FOREIGN_KEY_CHECKS = 1;
```