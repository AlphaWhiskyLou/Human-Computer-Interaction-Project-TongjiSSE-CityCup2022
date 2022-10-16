package com.banking.projectserver.controller;

import cn.hutool.jwt.JWTUtil;
import com.banking.projectserver.activeMQ.Consumer;
import com.banking.projectserver.activeMQ.Producer;
import com.banking.projectserver.aop.log.MessageLog;
import com.banking.projectserver.entity.MessageRecv;
import com.banking.projectserver.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-18 14:35:17
 */
@RestController
@RequestMapping("MessageCenter")
public class MessageController {

    @Autowired
    //创建一个生产者，消费者在系统运行时已经创建
    Producer producer;

    @Autowired
    Consumer consumer;

    @MessageLog
    @GetMapping( "SendMessageByQueue/{msg}/{queueName}")
    public Response send(@PathVariable String msg, @PathVariable String queueName) {
        //System.out.println(msg+"开始发出一次请求，时间是"+new Date());
        producer.sendMessage(msg, queueName);
        //System.out.println(msg+"请求发送完成，时间是"+new Date());
        return Response.OK();
    }

    @GetMapping("ReceiveMessageByQueue")
    public Response getMessage(HttpServletRequest request) throws JMSException, InterruptedException {
        String token = request.getHeader("token");
        String queueName = (String) JWTUtil.parseToken(token).getPayload("uid");
        //System.out.println("Message Received");
        //System.out.println(consumer.getMessage(queueName));
        MessageRecv msgRecv = null;
        String msg = consumer.getMessage(queueName);
        if(queueName.contentEquals(msg)){
            //System.out.println(1);
            msgRecv = new MessageRecv();
            msgRecv.setMessage(msg);
        }
        if (msgRecv == null) {
            //System.out.println(2);
            return Response.invalidService().message("No message received");
        }
        else{
            //System.out.println(3);
            return Response.OK().data("msgRecv",msgRecv);
        }
    }


}