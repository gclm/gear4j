# -*- coding:utf-8 -*-

import argparse
import os
import subprocess
import signal
import time

PUSH_TYPE_INFO = {
    "huawei": "华为云",
    "sonatype": "中央仓库",
    "rbc": "阿里云效",
    "coding": "腾讯云Coding"
}


def cmd(in_cmd_line, timeout=120):
    if len(in_cmd_line) <= 0:
        # _logger.error("invalid cmd line")
        return -1, None
    try:
        # _logger.info("start cmd {}".format(in_cmd_line))
        p = subprocess.Popen(in_cmd_line, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        try:
            outs, errs = p.communicate(timeout=timeout)  # outs为cmd命令行执行后显示到shell界面的结果
            retval = p.returncode  # 返回不为0时说明命令执行失败
        except subprocess.TimeoutExpired:
            os.kill(p.pid, signal.SIGKILL)
            # _logger.warning('cmd {} process killed,timer {} begin'.format(in_cmd_line, timeout))
            outs, errs = p.communicate()
            retval = p.returncode
            # _logger.warning('cmd {} process killed,timer {} end {}'.format(in_cmd_line, timeout, retval))

        # _logger.info("run cmd {} ret {} | {} | {}".format(in_cmd_line, retval, outs, errs))
        return retval, outs.decode("utf-8", "replace").replace("\n", ""), errs.decode()
    except Exception as e:
        # _logger.error("run cmd {} error {} - {}".format(in_cmd_line, e, traceback.format_exc()))
        return -1, None, None


def add_content_file(file, text):
    with open(file, 'r+') as f:
        content = f.read()
        f.seek(0, 0)
        f.write(text + "\n" + content)


def replace_file_content(file, src_text, dst_text):
    with open(file, 'r+') as f:
        content = f.read()
        content = content.replace(src_text, dst_text)
        f.seek(0, 0)
        f.write(content)


def parse_opt():
    parser = argparse.ArgumentParser(description="Chaos辅助脚本\t作者：孤城落寞")
    parser.add_argument('-i', '--info', action='version',
                        version='%(prog)s version : v1.0.0', help='查看脚本版本')
    parser.add_argument('-p', '--push', choices=['all', 'huawei', 'sonatype', 'rbc', 'coding'], help="发布依赖")
    parser.add_argument('-v', '--version', type=str, help='修改版本')
    parser.add_argument('-d', '--dependency', action='store_true', help='升级到最新依赖')
    parser.add_argument('-s', '--sonar', action='store_true', help='sonar扫描')
    parser.add_argument('-t', '--test', action='store_true', help='本地测试')
    return parser.parse_args()


def main():
    args = parse_opt()
    if args.push:
        print("start option push")
        push_type = args.push
        if "all" in push_type:
            print("项目发布 --> 腾讯云Coding")
            os.system("mvn clean deploy -P coding-oss-release")
            print("项目发布 --> 中央仓库")
            os.system("mvn clean deploy -P sonatype-oss-release")
        else:
            print("项目发布 --> {}".format(PUSH_TYPE_INFO[push_type]))
            os.system("mvn clean deploy -P {}-oss-release".format(push_type))
    if args.version:
        version = args.version
        date = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
        print("start option version")
        code, old_version, errs = cmd(
            "mvn -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive exec:exec")
        flag = input("版本: [{}] --> [{}]，是否更新？[Y/n]\n".format(old_version, version))
        if "n" in flag or code == 1:
            exit(0)
        result = os.system("mvn versions:set -DnewVersion={}".format(version))
        if result == 0:
            os.system('echo "{}\t\t发布版本：{}\t\t当前开发版本：{}" >> docs/version.txt'.format(date, old_version, version))
            add_content_file("CHANGELOG.md", "### 版本：{}\t\t发布时间：{}".format(old_version, date))
#             replace_file_content("README.md", old_version, version)
            print("版本：{}\t发布时间：{}\t当前最新版本：{}".format(old_version, date, version))
    if args.dependency:
        print("start option dependency")
        os.system("mvn versions:display-dependency-updates")
    if args.sonar:
        print("start option sonar")
        os.system('mvn sonar:sonar')
    if args.test:
        print("start option test")
        os.system('mvn clean install')


if __name__ == "__main__":
    main()
