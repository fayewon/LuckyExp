package com.kotei.service.service;

import javax.jms.Destination;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class ActiveService {
	//@Autowired
    //private JmsMessagingTemplate jmsMessagingTemplate;

    //@Autowired
    //private Queue queue;

    //@Autowired
    //private Topic topic;

    @PostMapping("/queue/test")
    public String sendQueue(@RequestBody String str) {
        //this.sendMessage(this.queue, str);
        return "success";
    }

    @PostMapping("/topic/test")
    public String sendTopic(@RequestBody String str) {
        //this.sendMessage(this.topic, str);
        return "success";
    }

    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendMessage(Destination destination, final String message){
       // jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
