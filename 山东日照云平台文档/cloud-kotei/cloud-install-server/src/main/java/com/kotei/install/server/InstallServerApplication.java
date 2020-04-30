package com.kotei.install.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @EnableEurekaServer
 * 设备消费者注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class InstallServerApplication 
{
	public static void main(String[] args) {
        SpringApplication.run(InstallServerApplication.class,args);
    }
}
