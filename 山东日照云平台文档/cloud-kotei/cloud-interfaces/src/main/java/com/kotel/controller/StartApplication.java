package com.kotel.controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@SpringBootApplication 
@EnableSwagger2
@EnableEurekaClient
public class StartApplication 
{
	
    @RequestMapping("/")
    String index(){
      return "ok";
    }
  
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源处理
        registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/META-INF/")
        .addResourceLocations("classpath:/hospitalpay");
    }
}
