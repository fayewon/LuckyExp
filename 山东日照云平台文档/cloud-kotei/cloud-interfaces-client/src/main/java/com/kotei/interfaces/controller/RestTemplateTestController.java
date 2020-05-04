package com.kotei.interfaces.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kotei.entity.Demo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/restTemplate")
@Slf4j
public class RestTemplateTestController {
	@Autowired
	private RestTemplate restTemplate;
	@GetMapping("/test/{name}")
	public List<Demo> index(@PathVariable String name) {
		String uri = "http://cloud-service-client/api/employees/";
		Map map = new HashMap();
		map.put("id", 123);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Map> request = new HttpEntity<>(map, headers);
		List<Demo> demo = restTemplate.getForObject( uri, List.class);
		log.info("response:" );
        return demo;
	}

}
