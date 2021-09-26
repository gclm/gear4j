<p align="center">
	<a href="https://github.com/gclm/chaos"><img src="https://cdn.jsdelivr.net/gh/gclm/images@master/20200414/1586827646660.png"></a>
</p>
<p align="center">
	<strong>一个功能强悍的 Spring Boot Starter 组件（Where dreams originate）</strong>
</p>
<p align="center">
	<a target="_blank" href="https://github.com/gclm/chaos/actions">
        <img src="https://github.com/gclm/chaos/actions/workflows/maven.yml/badge.svg" />
	</a>
    <a target="_blank" href="https://github.com/gclm/chaos/actions">
        <img src="https://github.com/gclm/chaos/actions/workflows/codeql-analysis.yml/badge.svg" />
	</a>
    <a href="https://www.codacy.com/gh/gclm/chaos/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gclm/chaos&amp;utm_campaign=Badge_Grade">
        <img src="https://app.codacy.com/project/badge/Grade/f1c25e46f1924c14a1d41f3718223402"/>
    </a>
    <a target="_blank" href="https://search.maven.org/search?q=g:%20club.gclmit%20AND%20a:%20chaos-spring-boot-starter">
        <img src="https://img.shields.io/maven-central/v/club.gclmit/chaos-spring-boot-starter.svg?label=Maven%20Central" />
	</a>
	<a target="_blank" href="https://github.com/gclm/chaos/blob/master/LICENSE/">
        <img src="https://img.shields.io/:license-Apache2-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-11+-green.svg" />
	</a>

[comment]: <> (<a href="https://codecov.io/gh/gclm/chaos">)

[comment]: <> (<img src="https://codecov.io/gh/gclm/chaos/branch/master/graph/badge.svg" />)

[comment]: <> (</a> )

[comment]: <> (    <a href="https://travis-ci.com/gclm/chaos">)

[comment]: <> (        <img src="https://travis-ci.com/gclm/chaos.svg?branch=master" />)

[comment]: <> (    </a>)
</p>
<p align="center">
    QQ群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=4684b1c1194706adcc4ce7c9428935d31c0b2a86b51e96cb807fa30f94cebfde">641633175</a>
</p>

## 项目简介

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
- 集成多家OSS存储商，可快速完成oss上传功能
- 集成请求日志记录，可以查看请求记录

### 项目结构

```text
.
├── LICENSE
├── README.md                       说明
├── chaos-spring-boot-starter       Spring Boot Starter     
├── chaos-core                      核心模块
├── chaos-logger                    日志模块
├── chaos-storage                   存储模块
├── chaos-waf                       安全模块
└── chaos-web                       web模块
```

## 使用教程

[Java Doc](https://apidoc.gitee.com/gclm/chaos/)

### 添加依赖

**Maven**

```xml

<dependency>
    <groupId>club.gclmit</groupId>
    <artifactId>chaos-spring-boot-starter</artifactId>
    <version>2.3.3</version>
</dependency>
```

**Gradle**

```text
implementation 'club.gclmit:chaos-spring-boot-starter:2.3.3'
```

### 导入SQL

```sql
SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chaos_file_info
-- ----------------------------
DROP TABLE IF EXISTS `chaos_file_info`;
CREATE TABLE `chaos_file_info`
(
    `id`           bigint(22) NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL DEFAULT '' COMMENT '文件名',
    `content_type` varchar(50)  NOT NULL DEFAULT '' COMMENT '文件类型',
    `url`          varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
    `size`         bigint(22) NOT NULL DEFAULT -1 COMMENT '文件大小',
    `md5`          varchar(50)  NOT NULL DEFAULT '' COMMENT 'md5',
    `e_tag`        varchar(50)  NOT NULL DEFAULT '' COMMENT 'ETag',
    `oss_key`      varchar(50)  NOT NULL DEFAULT '' COMMENT 'OSS key',
    `status`       int(11) NOT NULL DEFAULT -1 COMMENT '文件状态',
    `upload_time`  bigint(16) DEFAULT NULL COMMENT '上传时间',
    `oss_type`     int(5) DEFAULT NULL COMMENT '对象存储类型',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='chaos_file_info';

-- ----------------------------
-- Table structure for chaos_trace_info
-- ----------------------------
DROP TABLE IF EXISTS `chaos_trace_info`;
CREATE TABLE `chaos_trace_info`
(
    `id`              bigint(22) NOT NULL COMMENT '主键 编号',
    `client_ip`       varchar(50)  NOT NULL DEFAULT '' COMMENT '客户端请求ip',
    `uri`             varchar(100) NOT NULL DEFAULT '' COMMENT '客户端请求的路径',
    `content_type`    varchar(100) NOT NULL DEFAULT '' COMMENT '客户端请求方式',
    `session_id`      varchar(50)           DEFAULT NULL COMMENT 'session ID',
    `method`          varchar(50)  NOT NULL DEFAULT '' COMMENT '请求方法类型: restful 风格',
    `request_time`    bigint(15) NOT NULL DEFAULT -1 COMMENT '请求时间戳（秒）',
    `response_time`   bigint(15) NOT NULL DEFAULT -1 COMMENT '接口返回时间',
    `request_body`    text                  DEFAULT '' COMMENT 'requestBody',
    `response_body`   text                  DEFAULT '' COMMENT 'responseBody',
    `request_header`  text         NOT NULL DEFAULT '' COMMENT 'request 请求头',
    `response_header` text         NOT NULL DEFAULT '' COMMENT 'response 响应头',
    `user_agent`      varchar(255) NOT NULL DEFAULT '' COMMENT '用户代理',
    `http_code`       int(10) DEFAULT NULL COMMENT '状态码',
    `consuming_time`  bigint(15) DEFAULT NULL COMMENT '请求耗时（秒）',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='chaos_trace_info';

SET
FOREIGN_KEY_CHECKS = 1;
```

## 鸣谢

- 感谢 JetBrains 提供的非商业开源软件开发授权
- Thanks for non-commercial open source development authorization by JetBrains




