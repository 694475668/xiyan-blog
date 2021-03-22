#!/usr/bin/env bash

echo '=====开始更新夕颜源码镜像====='

echo '=====开始关闭运行的容器====='
sh kernShutdown.sh

echo '=====开始更新xiyan-gateway====='
docker pull registry.cn-shenzhen.aliyuncs.com/xiyan-blog/xiyan-gateway

echo '=====开始更新oss-service====='
docker pull registry.cn-shenzhen.aliyuncs.com/xiyan-blog/oss-service

echo '=====开始更新system-base-service====='
docker pull registry.cn-shenzhen.aliyuncs.com/xiyan-blog/system-base-service

echo '=====开始更新xiyan-web-service====='
docker pull registry.cn-shenzhen.aliyuncs.com/xiyan-blog/xiyan-web-service

echo '=====开始更新search-service====='
docker pull registry.cn-shenzhen.aliyuncs.com/xiyan-blog/search-service

echo '=====开始更新xx-job====='
docker pull registry.cn-shenzhen.aliyuncs.com/xiyan-blog/xxl-job-admin

echo '=====开始更新admin-server====='
docker pull registry.cn-shenzhen.aliyuncs.com/xiyan-blog/admin-server


echo '=====删除docker标签为none的镜像====='
docker images | grep none | awk '{print $3}' | xargs docker rmi

echo '=====开始运行的一键部署脚本====='
sh kernStartup.sh
