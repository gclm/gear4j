### 2.6.2

#### 新特性：

- feat: 新增redis限流方案和优化工具类函数
- feat: 删除备注字段
- feat: 新增自定义参数效验异常

#### bug修复

- fix: 修复redis cache bug
- fix: 修复文件上传bug
- fix: 修复redis cache bug
- fix: 修复文件上传bug
- fix: 修复PageResult构造器bug
- fix: 修复返回数据无data
- fix: 修复子模块无法生成配置文件

### 代码优化

- perf: 删除Jackson序列化和新增时间工具类转换
- perf: 所有自定义异常全部继承于Gear4jException
- perf: 使用lombok精简代码
- perf: 优化PageResult构造
- perf: 优化数据响应
- perf: 优化钉钉通知参数
- perf: 优化阿里云cos调用方法

#### 其他

- refactor: 移除web模块代码合并到stater模块中
- style: 优化pageResult记录名字
- Merge pull request #8 from gclm/dev
- Merge pull request #7 from gclm/imgbot
- [ImgBot] Optimize images
- Merge pull request #6 from gclm/dev

### 2.6.1

#### 新特性：

- feat: 新增redis模块
- feat: 优化上传图片格式和新增date过期方法
- feat: 删除多余依赖和新增自动发布快照功能
- feat: 新增多种图片类型
- feat: 新增SQL效验

#### bug修复

- fix: 修复GithubAction Build报错
- fix: 修复启动事件监听代码
- fix: 移除hutool-http依赖和修复IoUtils获取输入流的bug
- 🐛: 修复hutool-http漏洞问题
- 🐛: 基于alibaba开发规范修复代码警告问题
- 🐛: 基于审计插件修复cos模块的bug

### 代码优化

- perf: 优化filter传参问题
- ci: 优化github action配置
- refactor: safe-xss重构完成
- ⚡️: 优化CI性能
- ⚡️: 优化在System.currentTimeMillis()高并发场景下的性能问题

#### 代码调整

- 🎨: 调整log工具类位置
- style: cos模块重命名
- style: 优化服务显示效果
- style: 修复命名风格问题和补充自动注入配置
- style: Logs -> LoggerProvider
- docs: 补充类注释和包注释
- 📝: 修订文档错误和调整cos模块的描述

### 2.6.0

#### 架构调整

- 🎨: 项目名从chaos修改为gear4j
- 🎨: gear4j-storage调整为cos模块
- 🎨: 新增safe模块，主要管理waf业务
- 🎨: boot-starter 模块只做包依赖管理
- 🎨: 删除冗余的工具类逻辑尽量复用Spring和hutool逻辑

#### 新特性：

- 👷: 新增qodana扫描
- ✨: 新增公共字段
- ⚡️: 优化gradlePush配置

#### bug修复

- 💚: 修复Github Action构建错误
- 🐛: 修复okhttps spi错误
- 🐛: 调整命名规则和优化springdoc注解
- 🔥: 移除api版本注解

#### 其他

- 📝: 新增LICENSE文件
- 📝: 基于新的项目名调整相关文档
- ⬆️: 升级依赖

### 2.5.0

- 🐛: 修复测试junit测试错误
- 👷: 新增Github Action Javadoc编译
- 👷: 新增sync同步、javadoc生成CI
- 💚: 规范CI格式
- 🎨: 优化通用异常处理
- ✨: 新增$常量类
- 🎨: 调整模块结构，waf合并到web模块，新增extra模块
- 📝: 更新各模块readme文档
- ✨: 新增chaos详细文档

### 2.4.4

- 🎨: 优化获取文件后缀的逻辑和新增上传文件白名单🎨: 优化获取文件后缀的逻辑和新增上传文件白名单
- ✨: 新增springdoc支持
- 🎨: 新增jackson工具类和修复map to object🎨: 新增jackson工具类和修复map to object
- 🐛: 修复bom依赖bug
- 🐛: 修复UserAgentUtils 加载依赖错误
- 🔊: 更新日志

### 2.4.3

- feat: 新增随机成功UserAgent方法
- fix: 优化提供的文件上传方法
- build: 采用最小依赖的方法优化依赖
- fix: 修复chaos-bom模块缺少本项目依赖
- style: 调整javadoc参数
- style: 调整错误命名
- style: 基于idea格式化代码

### 2.4.2

- ci: 修复自动发布包异常

### 2.4.1

- ci: 修复github action
- ci: 使用sonar 优化代码
- fix: 修复Make sure not setting any maximum content length limit is safe here
- style: 规范Javadoc

### 2.4.0

- feat: maven to gradle
- feat: 合并bom代码到当前项目
- build: 升级依赖
- docs: 取消同步Javadoc文档到gitee
- style: 修改返回类型风格
- style: 删除注解@date
- style: 删除多余的.gitignore文件

### 2.3.7

- 新增`DefaultFileService`基于id删除的方法
- 新增`CHANGELOG.md`版本记录管理
- chaos.sh -> chaos.py
- requestClient 新增上传文件参数支持
- 优化GlobalExceptionHandler、RestApiController类注解
- 修复chaos-storage中阿里云失效的bug
- 优化函数注释
- 升级依赖

### 2.3.6

- 优化请求参数
- 修改参数命名
- 优化查看相关注解

### 2.3.5

- 优化通用Build参数
- 新增Github Action 生成JavaDoc 功能
