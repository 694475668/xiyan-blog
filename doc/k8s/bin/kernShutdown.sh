#!/usr/bin/env bash

echo '=====开始结束运行夕颜源码服务====='

kubectl delete ns xiyan

echo '=====所有程序结束中====='
../config/wait-for-it.sh --timeout=60  -- echo "=====所有程序结束中====="

echo '=============================='
echo '=====所有服务已经结束运行====='
echo '=============================='