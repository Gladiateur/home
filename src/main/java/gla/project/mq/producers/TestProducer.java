package gla.project.mq.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
生产者
 */
@Component
public class TestProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /*
    向队列发送消息
     */
    public void send(){
        String msg="msg";
        System.out.println(msg);
        rabbitTemplate.convertAndSend("hello-queue-test",msg);
    }
}
