#!/usr/bin/env bash

# 发布当前 jar 到远程 maven 中
# mvn clean deploy -P huaweicloud-oss-release
# mvn clean deploy -P sonatype-oss-release

# 字体样式
Green_font_prefix="\033[32m" && Red_font_prefix="\033[31m" && Green_background_prefix="\033[42;37m" && Red_background_prefix="\033[41;37m" && Font_color_suffix="\033[0m"
Info="${Green_font_prefix}[信息]${Font_color_suffix}"
Error="${Red_font_prefix}[错误]${Font_color_suffix}"
Tip="${Green_font_prefix}[注意]${Font_color_suffix}"
Ignore=("LICENSE" "README.md" "bin" "chaos.sql" "chaos-boot-starter")
OSS="huaweicloud-oss-release"

start_menu(){
clear
echo -e "
Maven Jar 一键发布脚本
  -- 孤城落寞博客 | blog.gclmit.club --
 ${Green_font_prefix}0.${Font_color_suffix} 退出脚本
———————————— 开发环境(Java) ————————————
 ${Green_font_prefix}1.${Font_color_suffix} 发布到 huaweicloud-oss-release
 ${Green_font_prefix}2.${Font_color_suffix} 发布到 sonatype-oss-release
——————————————————————————————————————
 ${Red_font_prefix}请根据具体情况进行操作${Font_color_suffix}
——————————————————————————————————————" && echo

read -p " 请输入选项 :" num
case "$num" in
	0)
	exit 1
	;;
    1)
	install
	;;
	2)
	OSS="sonatype-oss-release"
	install
	;;
	*)
	clear
	echo -e "${Error}:请输入正确选项："
	sleep 3s
	start_menu
	;;
esac
}
function contains() {
    local n=$#
    local value=${!n}
    for ((i=1;i < $#;i++)) {
        if [ "${!i}" == "${value}" ]; then
            echo "y"
            return 0
        fi
    }
    echo "n"
    return 1
}

function push() {
cd $path
mvn clean deploy -P $OSS
}

install(){
cd ..
base_path=$(pwd)
echo "当前文件路径: $base_path"
files=$(ls)
for filename in $files
do
    if [ $(contains "${Ignore[@]}" "$filename") == "n" ]; then
        path=$base_path"/"$filename
        echo $path  $OSS
        push
    fi

done
path=$base_path"/chaos-boot-starter"
push
}

start_menu