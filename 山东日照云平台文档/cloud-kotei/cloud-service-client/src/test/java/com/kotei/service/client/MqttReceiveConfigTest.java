package com.kotei.service.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kotei.service.config.mqtt.MqttReceiveConfig;

/**
 * mqtt测试
 * @author FayeWong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttReceiveConfigTest {
	//@Autowired
	MqttReceiveConfig mqttReceiveConfig;
	@Test
	public void test(){
		//mqttReceiveConfig.getMqttConnectOptions();
	}
}
