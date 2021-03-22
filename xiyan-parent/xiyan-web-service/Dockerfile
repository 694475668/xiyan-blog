###指定java8环境镜像
FROM java:8
###复制文件到容器app-springboot
ADD target/xiyan-web-service-1.0-SNAPSHOT.jar /xiyan-web-service.jar
###声明启动端口号
EXPOSE 8085
###配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","/xiyan-web-service.jar"]