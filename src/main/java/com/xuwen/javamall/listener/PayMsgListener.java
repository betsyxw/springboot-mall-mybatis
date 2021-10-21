package com.xuwen.javamall.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * author:xuwen
 * Created on 2021/10/21
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @RabbitHandler
    public void process(String msg){
        log.info("「接收到消息」{}",msg);

    }


}
