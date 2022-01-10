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

