# 所需基础镜像
FROM registry.cn-shenzhen.aliyuncs.com/xiyan-blog/nginx
# 作者信息
MAINTAINER bright
# 端口指定
EXPOSE 8090
# 文件拷贝 将打包后的前端项目 拷贝到。。。。。
COPY dist/ /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/nginx.conf
COPY xiyan.conf /etc/nginx/conf.d/default.conf

