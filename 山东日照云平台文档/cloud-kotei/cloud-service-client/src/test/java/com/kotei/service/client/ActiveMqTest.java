package com.kotei.service.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kotei.service.config.activeMq.ActiveMqConfig;


/**
 * 消息队列测试
 * @author FayeWong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveMqTest {
	//@Autowired
	ActiveMqConfig activeMqConfig;
	@Test
	public void test(){
		//activeMqConfig.connectionFactory();
	}

}
