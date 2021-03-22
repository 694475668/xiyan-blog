#!/usr/bin/env bash

echo '=====开始结束运行夕颜源码服务====='

echo '=====结束运行portainer可视化工具====='
docker-compose -f ../yaml/portainer.yml down

echo '=====结束运行mysql====='
docker-compose -f ../yaml/mysql.yml down

echo '=====结束运行nacos====='
docker-compose -f ../yaml/nacos.yml down

echo '=====结束运行seata====='
docker-compose -f ../yaml/seata.yml down

echo '=====结束运行kafka====='
docker-compose -f ../yaml/kafka.yml down

echo '=====结束运行redis====='
docker-compose -f ../yaml/redis.yml down

echo '=====结束运行nginx====='
docker-compose -f ../yaml/nginx.yml down

echo '=====结束运行zipkin====='
docker-compose -f ../yaml/zipkin.yml down

echo '=====结束运行sentinel====='
docker-compose -f ../yaml/sentinel.yml down

echo '=====结束运行ELFK====='
docker-compose -f ../yaml/elfk.yml down


echo '=========================='
echo '=====结束后台服务运行====='
echo '=========================='

echo '=====结束运行xxl-job====='
docker-compose -f ../yaml/xxl-job.yml down

echo '=====结束运行admin-server====='
docker-compose -f ../yaml/admin-server.yml down

echo '=====结束运行xiyan-gateway====='
docker-compose -f ../yaml/xiyan-gateway.yml down

echo '=====结束运行xiyan-web-service====='
docker-compose -f ../yaml/xiyan-web-service.yml down

echo '=====结束运行search-service====='
docker-compose -f ../yaml/search-service.yml down

echo '=====结束运行system-base-service====='
docker-compose -f ../yaml/system-base-service.yml down

echo '=====结束运行oss-service====='
docker-compose -f ../yaml/oss-service.yml down

echo '=====结束运行pay====='
docker-compose -f ../yaml/pay.yml down


echo '=========================='
echo '=====结束前台服务运行====='
echo '=========================='

echo '=====结束运行vue-xiyan-web====='
docker-compose -f ../yaml/vue-xiyan-web.yml down


echo '=============================='
echo '=====所有服务已经结束运行====='
echo '=============================='