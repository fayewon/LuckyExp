package com.kotei.service.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kotei.entity.Demo;
import com.kotei.service.config.redis.RedisUtils;
/**
 * redis测试
 * @author FayeWong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
 
  //@Autowired
  private RedisUtils redisUtils;
 
  @Test
  public void test(){
	  Demo demo  = new Demo();
	  demo.setId(123);
	  demo.setUrl("Zhangsan");
    //redisUtils.set("demo-001", demo);
    //System.out.println(redisUtils.get("demo-001"));
  }
 
}
