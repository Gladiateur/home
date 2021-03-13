package gla.project.mq.controller;

import gla.project.mq.producers.TestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class MQController {
    @Autowired
    private TestProducer testProducer;

    @GetMapping("/send")
    public String test(){
        testProducer.send();
        return "success";
    }
}
