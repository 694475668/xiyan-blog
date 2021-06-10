###指定java8环境镜像
FROM java:8
###复制文件到容器app-springboot
ADD target/user-oauth2-auth-1.0-SNAPSHOT.jar /user-oauth2-auth.jar
###声明启动端口号
EXPOSE 8083
###配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","/user-oauth2-auth.jar"]