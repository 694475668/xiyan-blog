#!/usr/bin/env bash

echo '=====开始清空博客中的数据====='

echo '=====开始清空博客数据====='
rm -rf ./xiyan_data/*

echo '=====开始清空nginx_data数据====='
rm -rf ./nginx_data/*

echo '=====开始清空mysql数据====='
rm -rf ./mysql_data/*

echo '=====开始清空portainer数据====='
rm -rf ./portainer/data/*

echo '=====开始清空redis数据====='
rm -rf ./redis_data/*

echo '=====开始清空elasticsearch_data数据====='
rm -rf ./elasticsearch_data/*


echo '=====开始清空log日志====='
rm -rf ../log/*

echo '=============================='
echo '=====所有数据已经被清空======='
echo '=============================='
