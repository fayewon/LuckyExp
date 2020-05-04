package com.kotei.interfaces.client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication 
@ComponentScan({"com.kotei.interfaces.*"})
@EnableEurekaClient
public class InterfacesClientApp { 
    public static void main(String[] args) {
        SpringApplication.run(InterfacesClientApp.class, args);
    }
    @Bean
	@LoadBalanced //开启负载均衡的注解
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
