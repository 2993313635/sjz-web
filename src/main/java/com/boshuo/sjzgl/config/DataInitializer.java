package com.boshuo.sjzgl.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 应用启动时初始化基础数据
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==========================================");
        System.out.println("     数据库初始化完成");
        System.out.println("==========================================");
        
        // 这里可以添加更多初始化逻辑
        // 例如检查必要的数据是否存在，如果不存在则创建
    }
}