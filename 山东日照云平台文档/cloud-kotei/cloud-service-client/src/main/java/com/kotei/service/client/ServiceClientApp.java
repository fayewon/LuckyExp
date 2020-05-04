package com.kotei.service.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
/**
 * @EnableEurekaServer
 * 服务提供注册中心
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.kotei.service.*")
@MapperScan("com.kotei.service.mapper")
public class ServiceClientApp 
{
	public static void main(String[] args) {
		
        SpringApplication.run(ServiceClientApp.class,args);
    }
	@Bean
	@LoadBalanced //开启负载均衡的注解
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
