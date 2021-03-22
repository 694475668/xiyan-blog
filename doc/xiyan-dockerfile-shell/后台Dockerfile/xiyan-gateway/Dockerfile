###指定java8环境镜像
FROM java:8
###复制文件到容器app-springboot
ADD xiyan-gateway-1.0-SNAPSHOT.jar /xiyan-gateway.jar
###声明启动端口号
EXPOSE 8084
###配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","/xiyan-gateway.jar"]
