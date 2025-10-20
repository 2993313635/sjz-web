#!/bin/bash
# 生产环境部署脚本

# 设置变量
APP_NAME="game-guide-backend"
APP_PORT=8080
JAR_FILE="target/$APP_NAME-1.0.0.jar"
LOG_FILE="logs/application.log"

# 创建日志目录
mkdir -p logs

# 检查是否已经运行
if [ -f "app.pid" ]; then
    PID=$(cat app.pid)
    if ps -p $PID > /dev/null; then
        echo "应用已经在运行 (PID: $PID)"
        exit 1
    else
        rm -f app.pid
    fi
fi

# 启动应用
echo "正在启动应用..."
nohup java -jar -Dspring.profiles.active=prod $JAR_FILE > $LOG_FILE 2>&1 &
PID=$!
echo $PID > app.pid

# 等待应用启动
echo "等待应用启动..."
sleep 30

# 检查应用是否启动成功
if ps -p $PID > /dev/null; then
    echo "应用启动成功 (PID: $PID)"
    echo "日志文件: $LOG_FILE"
    echo "访问地址: http://localhost:$APP_PORT/api"
else
    echo "应用启动失败"
    cat $LOG_FILE
    exit 1
fi