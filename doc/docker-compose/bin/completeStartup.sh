#!/usr/bin/env bash

echo '=====开始安装夕颜源码环境====='

echo '=====开始运行mysql====='
docker-compose -f ../yaml/mysql.yml up -d
echo '=====mysql正在进行初始化====='
../config/wait-for-it.sh http://localhost:3306 --timeout=60  -- echo "=====mysql已经准备就绪====="

echo '=====开始部署portainer可视化工具====='
docker-compose -f ../yaml/portainer.yml up -d

echo '=====开始运行nacos====='
docker-compose -f ../yaml/nacos.yml up -d
echo '=====nacos正在进行初始化,请等待...====='
../config/wait-for-it.sh http://localhost:8848 --timeout=60  -- echo "=====nacos已经准备就绪====="

echo '=====开始部署seata====='
docker-compose -f ../yaml/seata.yml up -d

echo '=====开始部署kafka====='
docker-compose -f ../yaml/kafka.yml up -d

echo '=====开始运行redis====='
docker-compose -f ../yaml/redis.yml up -d

echo '=====开始运行zipkin====='
docker-compose -f ../yaml/zipkin.yml up -d

echo '=====开始运行sentinel====='
docker-compose -f ../yaml/sentinel.yml up -d

echo '=====开始部署nginx====='
docker-compose -f ../yaml/nginx.yml up -d

echo '=====开始运行ELFK====='
docker-compose -f ../yaml/elfk.yml up -d
echo '=====elfk正在进行初始化,请等待...====='
../config/wait-for-it.sh http://localhost:5601 --timeout=60  -- echo "=====ELFK已经准备就绪====="

echo '======================'
echo '=====开始运行后台====='
echo '======================'


echo '=====开始运行xxl-job====='
docker-compose -f ../yaml/xxl-job.yml up -d
echo '=====xxl-job正在进行初始化,请等待...====='
../config/wait-for-it.sh http://localhost:8088 --timeout=60  -- echo "=====nacos已经准备就绪====="

echo '=====开始运行admin-server====='
docker-compose -f ../yaml/admin-server.yml up -d

echo '=====开始运行xiyan-gateway====='
docker-compose -f ../yaml/xiyan-gateway.yml up -d

../config/wait-for-it.sh http://localhost:port --timeout=60  -- echo "=====已经准备就绪====="

echo '=====开始运行xiyan-web-service====='
docker-compose -f ../yaml/xiyan-web-service.yml up -d


echo '=====开始运行system-base-service====='
docker-compose -f ../yaml/system-base-service.yml up -d


../config/wait-for-it.sh http://localhost:port --timeout=60  -- echo "=====已经准备就绪====="


echo '=====开始运行search-service====='
docker-compose -f ../yaml/search-service.yml up -d

echo '=====开始运行oss-service====='
docker-compose -f ../yaml/oss-service.yml up -d

../config/wait-for-it.sh http://localhost:port --timeout=60  -- echo "=====已经准备就绪====="

echo '=====开始运行pay====='
docker-compose -f ../yaml/pay.yml up -d


echo '======================'
echo '=====开始运行前台====='
echo '======================'

echo '=====开始部署前端源码====='
docker-compose -f ../yaml/vue-xiyan-web.yml up -d

echo '执行完成 日志目录: ./log'


echo '======================================================'
echo '=====所有服务已经启动【请检查是否存在错误启动的】====='
echo '======================================================'
