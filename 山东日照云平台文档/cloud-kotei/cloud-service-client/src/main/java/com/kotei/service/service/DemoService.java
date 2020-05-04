package com.kotei.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kotei.entity.Demo;
import com.kotei.service.mapper.DemoAnnotationMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DemoService {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	DemoAnnotationMapper demoAnnotationMapper;
	@GetMapping("/api/employees")
	@Transactional//事物
	public List<Demo> findAll(){
		System.out.println("==================");
		log.debug("id: ");
		List<Demo> demos = demoAnnotationMapper.findAll();
		//restTemplate.postForEntity(url, request, responseType)
		return demos;
	}
}
