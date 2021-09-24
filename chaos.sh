#!/usr/bin/env bash

# 定义常量
nexus=("huawei" "sonatype" "rbc")
date=$(date "+%Y-%m-%d %H:%M:%S")
old_version=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec)
name=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.artifactId}' --non-recursive exec:exec)
pwd=$(pwd)

# 帮助信息
help(){
cat <<- eof
--------------------------------------------------------------------------
desc: 项目通用脚本
usage: ./chaos.sh (-option 操作) (-param 配置参数)
author: 孤城落寞
程序选项如下：
    -h --help         帮助文档
    -v --version      修改版本信息
    -p --push         发布代码
      -all            发布到下面所有仓库
      -huawei         发布到 华为云
      -sonatype       发布到 Sonatype
      -rbc            发布到 阿里云
      -coding         发布到 腾讯云 Coding
    -d --dependency   最新依赖
    -s --sonar        bug 扫描
    -t --test         本地测试

脚本版本： v1.1.2
--------------------------------------------------------------------------
eof
exit 0
}

# 判断文件是否存在
contains() {
    local n=$#
    local value=${!n}
    if [[ ${value} == *iml* || ${value} == *sh* ]]; then
        echo "y"
        return 0
    fi
    for ((i=1;i < $#;i++)) {
        if [[ "${value}" == "${!i}" ]]; then
            echo "y"
            return 0
        fi
    }
    echo "n"
    return 1
}


# 发布当前 jar 到远程 maven 中
# mvn clean deploy -P huawei-oss-release
# mvn clean deploy -P sonatype-oss-release
# mvn clean deploy -P rbc-oss-release
push(){
    echo "项目目录: $pwd 当前选择参数 $oss"
    if [[ ${oss} == "all" ]]; then
#        echo "项目发布 --> 华为云"
#        mvn clean deploy -P huawei-oss-release
#        echo "项目发布 --> 阿里云"
#        mvn clean deploy -P rbc-oss-release
        echo "项目发布 --> 腾讯云Coding"
        mvn clean deploy -P coding-oss-release
        echo "项目发布 --> 中央仓库"
        mvn clean deploy -P sonatype-oss-release
    else
        mvn clean deploy -P ${oss}-oss-release
    fi
}

#------------------------------------------------
# 升级版本，包括：
# 1. 升级pom.xml中的版本号
# 2. 替换README.md的版本号
# 3. 增加版本日志记录
#------------------------------------------------
version(){
    echo "版本:[$old_version] --> [$version]，是否更新？[Y/n]"
    read -p "(默认: y):" yn
    [[ -z "${yn}" ]] && yn="y"
    if [[ ${yn} == [Yy] ]]; then
        # 替换所有模块pom.xml中的版本
        mvn versions:set -DnewVersion=$version
        # 替换README.md中的版本
#            sed -i "s/${old_version}/${version}/g" $pwd/README.md
        # 添加版本日志
        echo -e "$date\t\t发布版本：$old_version\t\t当前最新版本：$version" >> $pwd/docs/version.txt
        echo  "当前pom已经更新到$version"
    else
        echo && echo "	已取消..." && echo
    fi
}

# 项目信息
info(){
    echo "--------------------------------------"
    echo "项目名称：$name"
    echo "项目位置：$pwd"
    echo "当前版本：$old_version"
    echo "--------------------------------------"
    exit 0
}

# 扫描代码
sonar(){
   mvn sonar:sonar
   exit 0
}

# 更新依赖版本
dependency(){
    mvn versions:display-dependency-updates
    exit 0
}

# 本地测试
local_test(){
   mvn clean install
   exit 0
}

# 定义项目
#echo "指令1：$1 指令2：$2"
while [[ -n "$1" ]];do #这里通过判断$1是否存在
  case $1 in
   -p|--push)
        oss=$2
        push
        shift 2
        break
   ;;
   -v|--version)
        version=$2
        version
        shift 2
        break
   ;;
   -i|--info)
        info
   ;;
   -s|--sonar)
        sonar
   ;;
   -d|--dependency)
        dependency
   ;;
   -t|--test)
        local_test
   ;;
   -h|--help)
        help
   ;;
   --)
        shift;break;; # end of options
   -*) echo "error: no such option $1."; exit 1;;
   *) break;;
   esac
done