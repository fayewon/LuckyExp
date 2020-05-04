package com.kotei.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @EnableEurekaServer
 * 启动注册中心，默认关闭
 */
@SpringBootApplication
@EnableEurekaServer
@ComponentScan("com.kotei.eureka.*")
public class EurekaServerApplication 
{
	public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }
}
