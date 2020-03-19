# chaos-projects

##  项目如下
```text
.
├── LICENSE
├── README.md                       说明
├── chaos-boot-starter             
├── chaos-core                      核心模块
├── chaos-logger                    日志模块
├── chaos-parent                    项目父类
├── chaos-dependencies              三方依赖
├── chaos-storage                   存储模块
├── chaos-swagger                   文档模块
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
  `request` text DEFAULT '' COMMENT 'requestBody',
  `response` text DEFAULT '' COMMENT 'responseBody',
  `request_header` text NOT NULL DEFAULT '' COMMENT 'request 请求头',
  `response_header` text NOT NULL DEFAULT '' COMMENT 'response 响应头',
  `user_agent` varchar(255) NOT NULL DEFAULT '' COMMENT '用户代理',
  `http_status_code` int(10) DEFAULT NULL COMMENT '状态码',
  `consuming_time` bigint(15) DEFAULT NULL COMMENT '请求耗时（秒）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='chaos_trace_info';

```