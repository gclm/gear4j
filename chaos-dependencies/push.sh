#!/usr/bin/env bash

echo "发布到 华为云"
mvn clean deploy -P huawei-oss-release

echo "发布到 sonatype"
mvn clean deploy -P sonatype-oss-release

echo "打开 sonatype: https://oss.sonatype.org/"

