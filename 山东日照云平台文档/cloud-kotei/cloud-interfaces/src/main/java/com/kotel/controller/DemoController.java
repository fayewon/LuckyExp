package com.kotel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
	@RequestMapping("/test")
	public String index() {
		return "hellowolrd";
	}

}
