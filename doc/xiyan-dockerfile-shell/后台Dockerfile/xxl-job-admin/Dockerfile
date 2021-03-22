###指定java8环境镜像
FROM java:8
###复制文件到容器app-springboot
ADD xxl-job-admin-2.2.1-SNAPSHOT.jar /xxl-job-admin.jar
###声明启动端口号
EXPOSE 8097
###配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","/xxl-job-admin.jar"]
