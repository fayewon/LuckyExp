package com.kotei.interfaces.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author fayewong
 * swagger2配置信息
 *
 */
@Configuration
@EnableSwagger2
//@ConditionalOnExpression("${swagger.enable}")
public class Swagger2Configuration extends WebMvcConfigurationSupport{
	private final static  String  SWAGGER_SCAN_BASE_PACKAGE = "com.kotei.interfaces.controller";
	public final  static  String  VERSION = "1.0.0";
	//@Value("${swagger.enable}") private boolean enable;
	 
    @Bean
    public Docket createAccepterRestApi() {
    	System.out.println("=============swagger2====================");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("业务数据模块API")//分组名,不指定默认为default
                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))// 扫描的包路径
                .paths(PathSelectors.any())// 定义要生成文档的Api的url路径规则
                .build()
                .apiInfo(apiInfo())// 设置swagger-ui.html页面上的一些元素信息
                .enable(true);
    }
 
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("山东日照云服务RESTful API接口文档")
                .description("提供业务数据接收模块/业务数据处理模块的文档")
                .termsOfServiceUrl("http://127.0.0.1:8081/")
                .version(VERSION)
                
                .build();
    }	  
  //addResourceHandlers方法添加了两个资源处理程序，
    //这段代码的主要作用是对Swagger UI的支持。(访问接口页面为空白时可加上)
   @Override
   protected void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("swagger-ui.html")
               .addResourceLocations("classpath:/META-INF/resources/");

       registry.addResourceHandler("/webjars/**")
               .addResourceLocations("classpath:/META-INF/resources/webjars/");

   }
}
