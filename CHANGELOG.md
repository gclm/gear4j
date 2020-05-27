# CHANGELOG

# 1.5.0

## 重构 chaos-logger模块
- 解决中文乱码问题 和 Filter 注入问题
- 优化 requestUtils clientIp
- 解决 Inteceptor 拦截器 无法 @AutoWried 错误
- 优化LoggerMapper 方法
- 其他细节优化

## Java Doc 重构
- 完善的 java Doc
- 基于 Gitee 生成 Java Doc 在线文档

## 细节优化

- 增加 gitee 作为备份镜像
- 修改文档
- 增加 travis 和 codecov 脚本
- 增加部分细节优化


# 1.4.10

1. 抽象集合工具类
2. 抽象net工具类
3. 优化部分代码
4. 优化部分工具包
5. 优化代码逻辑

# 1.4.9
1. 修改部分断言逻辑错误
2. 修改logger模块的 loggger 配置
3. 升级HttpUtils 为HttpRequestUtils
4. 重构xss过滤为动态开启
5. 优化webconfig，删除无用代码
6. 优化代码组件
7. 其他bug修改

# 1.4.8
```
chaos-core：
1. 优化 chaos-core 的IOUtils 和 StringUtils
2. HttpUtils 增加方法

chaos-logger:
1. 增加构造器模式
2. 重构代码，采用异步保存数据库
3. 优化依赖

chaos-store:
1. 腾讯云命名修改
2. UFile删除集合代码修改

chaos-annotations:
1. EnableChaos 注解增加异步操作
```