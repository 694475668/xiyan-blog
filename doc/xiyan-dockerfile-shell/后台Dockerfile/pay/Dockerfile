###指定java8环境镜像
FROM java:8
###复制文件到容器app-springboot
ADD mq-0.0.1-SNAPSHOT.war /pay.war
###声明启动端口号
EXPOSE 6690
###配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","/pay.war"]
