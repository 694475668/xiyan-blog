#!/usr/bin/env bash

echo '=====开始部署K8S集群====='

../k8s-install/k8sDeploy.sh


echo '=====开始安装夕颜源码环境====='


echo '=====开始创建命名空间====='
kubectl apply -f ../yaml/xiyan-namespace.yaml


echo '=====开始部署mysql====='
kubectl apply -f ../yaml/mysql.yaml

echo '=====mysql正在进行初始化====='
../config/wait-for-it.sh http://localhost:32306 --timeout=60  -- echo "=====mysql已经准备就绪====="

echo '=====开始部署dashboard可视化工具====='
#kubectl apply -f ../yaml/dashboard.yaml

echo '=====开始运行nacos====='
kubectl apply -f ../yaml/nacos.yaml

echo '=====nacos正在进行初始化,请等待...====='
../config/wait-for-it.sh http://localhost:32308 --timeout=60  -- echo "=====nacos已经准备就绪====="

echo '=====开始部署seata====='
kubectl apply -f ../yaml/seata.yaml


echo '=====开始部署zookeeper====='
kubectl apply -f ../yaml/zookeeper.yaml

sleep 30

echo '=====开始部署kafka====='
kubectl apply -f ../yaml/kafka.yaml

echo '=====开始部署elasticsearch====='
kubectl apply -f ../yaml/elasticsearch.yaml

echo '=====开始部署redis====='
kubectl apply -f ../yaml/redis.yaml

echo '=====开始部署ingress====='
kubectl apply -f ../yaml/ingress-controller.yaml

sleep 30

echo '=====开始部署xiyan-ingress====='
kubectl apply -f ../yaml/xiyan-ingress.yaml

echo '=====kafka,elasticsearch,redis正在进行初始化,请等待...====='
../config/wait-for-it.sh http://localhost:port --timeout=60  -- echo "=====kafka,elasticsearch,redis已经准备就绪====="

echo '======================'
echo '=====开始运行后台====='
echo '======================'

echo '=====开始运行xxl-job====='
kubectl apply -f ../yaml/xxl-job.yaml
echo '=====xxl-job正在进行初始化,请等待...====='
../config/wait-for-it.sh http://localhost:32317 --timeout=60  -- echo "=====nacos已经准备就绪====="

echo '=====开始运行xiyan-gateway====='
kubectl apply -f ../yaml/xiyan-gateway.yaml

sleep 30

echo '=====开始运行xiyan-web-service====='
kubectl apply -f ../yaml/xiyan-web-service.yaml


../config/wait-for-it.sh http://localhost:port --timeout=60  -- echo "=====已经准备就绪====="

echo '=====开始运行system-base-service====='
kubectl apply -f ../yaml/system-base-service.yaml

sleep 30

echo '=====开始运行search-service====='
kubectl apply -f ../yaml/search-service.yaml

sleep 30


echo '=====开始运行oss-service====='
kubectl apply -f ../yaml/oss-service.yaml

../config/wait-for-it.sh http://localhost:port --timeout=60  -- echo "=====已经准备就绪====="

echo '=====开始运行pay====='
kubectl apply -f ../yaml/pay.yaml


echo '======================'
echo '=====开始运行前台====='
echo '======================'

echo '=====开始部署前端源码====='
kubectl apply -f ../yaml/vue-xiyan-web.yaml

echo '=====开始部署ingress====='
kubectl apply -f ../yaml/ingress-controller.yaml
kubectl apply -f ../yaml/xiyan-ingress.yaml


echo '执行完成 日志目录: ../data'


echo '======================================================'
echo '=====所有服务已经启动【请检查是否存在错误启动的】====='
echo '======================================================'
