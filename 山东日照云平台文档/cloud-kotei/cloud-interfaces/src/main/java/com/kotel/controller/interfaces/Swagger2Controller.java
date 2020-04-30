package com.kotel.controller.interfaces;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "接口文档测试")
@RestController
@RequestMapping("/swagger2")
public class Swagger2Controller {
	@ApiOperation(value="发送post解析文本", notes="发送解析文本", produces="application/json")
    @RequestMapping(value="/sendText", produces={ MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes={ MediaType.APPLICATION_JSON_UTF8_VALUE }, method=RequestMethod.POST)
    public String sendText(@RequestBody String text) {      
        
        
        return text;
    }
}
