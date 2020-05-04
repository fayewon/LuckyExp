package com.kotei.service.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kotei.service.mapper.DemoAnnotationMapper;

import lombok.extern.slf4j.Slf4j;
/**
 * mybatis测试
 * @author FayeWong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DBTest {
	@Autowired
	DemoAnnotationMapper demoAnnotationMapper;

    @Test
    public void contextLoads() {
    	log.info("================111");
        System.out.println(demoAnnotationMapper.findAll());
    }

}
