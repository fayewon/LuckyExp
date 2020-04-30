package com.cloud.service.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @EnableEurekaServer
 * 服务提供注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceServerApplication 
{
	public static void main(String[] args) {
        SpringApplication.run(ServiceServerApplication.class,args);
    }
}
