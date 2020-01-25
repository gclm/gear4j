# chaos-projects

##  项目如下
```text

├── LICENSE
├── README.md                       说明
├── chaos                           common模块
├── chaos-dependencies              Jar版本管理
├── chaos-logger                    日志模块
├── chaos-parent                    项目父类   
├── chaos-security                  权限模块
│   ├── chaos-security-app          APP/小程序模块
│   ├── chaos-security-browser      浏览器模块 
│   ├── chaos-security-core         security 核心
│   └── chaos-security-demo         security-demo
├── chaos-storage
└── docs                            使用文档
    ├── LICENSE
    ├── README.md
    └── chaos.sql                   项目SQL
```
**项目结构示意图**
![1579929751198ydEx5A](https://cdn.jsdelivr.net/gh/gclm/images@master/2020/01/25/1579929751198ydEx5A.png)


## 组件建表语句

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chaos_UserConnection
-- ----------------------------
DROP TABLE IF EXISTS `chaos_UserConnection`;
CREATE TABLE `chaos_UserConnection` (
  `userId` varchar(255) NOT NULL,
  `providerId` varchar(255) NOT NULL,
  `providerUserId` varchar(255) NOT NULL,
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(512) DEFAULT NULL,
  `imageUrl` varchar(512) DEFAULT NULL,
  `accessToken` varchar(512) NOT NULL,
  `secret` varchar(512) DEFAULT NULL,
  `refreshToken` varchar(512) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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

SET FOREIGN_KEY_CHECKS = 1;
```