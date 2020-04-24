#!/bin/bash

#------------------------------------------------
# 升级Chaos版本，包括：
# 1. 升级pom.xml中的版本号
# 2. 替换README.md的版本号
# 3. 增加版本日志记录
#------------------------------------------------

# 更新版本
update_version(){
 # 替换所有模块pom.xml中的版本
 mvn versions:set -DnewVersion=$version
 # 替换README.md中的版本
 # sed -i "s/${old_version}/${version}/g" $pwd/README.md
 # 替换docs/index.html中的版本
 #sed -i "s/${old_version}/${new_version}/g" $pwd/docs/js/version.js
 # 保留新版本号
 content=$(date "+%Y-%m-%d %H:%M:%S")"\t\t发布版本：$version"
 echo -e $content >> $pwd/docs/version.txt
}

if [ ! -n "$1" ]; then
    echo "ERROR: 新版本不存在，请指定参数1"
    exit
fi

cd ../
version=$1
old_version=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec)
pwd=$(pwd)
echo "当前路径：${pwd}"
echo "旧版本：$old_version"
echo "新版本：$version"

if [[ ${version} > ${old_version} ]]; then
    echo "正在升级 POM 版本；$old_version --> $version ，是否更新？[Y/n]"
else
    echo "正在回退 POM 版本；$old_version --> $version ，是否更新？[Y/n]"
fi

read -p "(默认: y):" yn
[[ -z "${yn}" ]] && yn="y"
if [[ ${yn} == [Yy] ]]; then
    update_version
    echo  "当前pom已经更新到$version"
else
    echo && echo "	已取消..." && echo
fi
