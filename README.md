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
</p>
<p align="center">
    QQ群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=4684b1c1194706adcc4ce7c9428935d31c0b2a86b51e96cb807fa30f94cebfde">641633175</a>
</p>

##  项目如下
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
**项目结构示意图**

## 组件建表语句

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
  `uri` varchar(50) NOT NULL DEFAULT '' COMMENT '客户端请求的路径',
  `content_type` varchar(50) NOT NULL DEFAULT '' COMMENT '客户端请求方式',
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