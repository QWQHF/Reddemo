# 使用 OpenJDK 17 作为基础镜像
FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件到容器内
COPY target/demo-0.0.1-SNAPSHOT.jar /app/redpartystudy.jar

# 设置容器启动时运行的命令
ENTRYPOINT ["java", "-jar", "redpartystudy.jar"]

# 暴露应用运行的端口（假设是 8080）
EXPOSE 8080