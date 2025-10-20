@echo off
REM 生产环境部署脚本 (Windows版本)

REM 设置变量
set APP_NAME=game-guide-backend
set JAR_FILE=target\%APP_NAME%-1.0.0.jar
set LOG_FILE=logs\application.log

REM 创建日志目录
if not exist "logs" mkdir logs

REM 检查是否已经运行
if exist "app.pid" (
    set /p PID=<app.pid
    tasklist /fi "PID eq %PID%" | findstr "PID" >nul
    if %errorlevel% equ 0 (
        echo 应用已经在运行 (PID: %PID%)
        exit /b 1
    ) else (
        del /f app.pid
    )
)

REM 启动应用
echo 正在启动应用...
start /b java -jar -Dspring.profiles.active=prod %JAR_FILE% > %LOG_FILE% 2>&1
echo %PID% > app.pid

REM 等待应用启动
echo 等待应用启动...
timeout /t 30 /nobreak >nul

echo 应用启动完成
echo 日志文件: %LOG_FILE%
echo 访问地址: http://localhost:8080/api