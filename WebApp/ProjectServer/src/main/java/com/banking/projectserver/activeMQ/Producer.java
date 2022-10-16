package com.banking.projectserver.activeMQ;

import org.apache.activemq.*;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-18 14:31:14
 */
@Service
public class Producer {
    public void sendMessage(String msg, String queueName) {
        try {
            //创建连接工厂,三个参数分别是用户名、密码以及消息队列所在地址
            ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD,
                    "tcp://101.132.250.23:61616");
            //连接到JMS提供者
            Connection conn = connFactory.createConnection();
            //开启连接
            conn.start();
            //事务性会话，自动确认消息
            Session session = conn.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
            //消息的目的地，创建队列"queue"
            Destination destination = session.createQueue(queueName);
            //消息生产者
            MessageProducer producer = session.createProducer(destination);
            //键值对消息
            MapMessage mapMessage = session.createMapMessage();
            //将消息内容放入到消息里
            mapMessage.setString("reqDesc", msg);
            //生产者传送消息
            producer.send(mapMessage);
            session.commit(); //提交会话，该条消息会进入"queue"队列，生产者也完成了历史使命
            producer.close();
            session.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}