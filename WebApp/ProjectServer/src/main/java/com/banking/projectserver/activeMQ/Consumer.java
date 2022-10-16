package com.banking.projectserver.activeMQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Date;
import java.util.concurrent.*;

import static java.lang.System.*;
import static java.lang.Thread.sleep;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-18 14:33:25
 */

@Service
public class Consumer {
    public String getMessage(String queueName) throws JMSException, InterruptedException {
        //前期的初始化工作与生产者相同
        boolean hasMessage = false;
        String messageRecv = "";
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://101.132.250.23:61616"
        );
        Connection conn = factory.createConnection();
        conn.start();
        Session session = conn.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        //与生产者的消息目的地相同
        Destination dest = session.createQueue(queueName);
        MessageConsumer consumer = session.createConsumer(dest);
        while(true) {
            MapMessage m = (MapMessage) consumer.receive(1500L);
            if(m != null) {
                String msg = m.getString("reqDesc");
                if(msg.contentEquals(queueName)){
                    hasMessage = true;
                    messageRecv = msg;
                }
                m.acknowledge();
            }else {
                break;
            }
        }
        //System.out.println("consumer接收到"+m.getString("reqDesc")+"的请求并处理完毕，时间是"+new Date());
        consumer.close();
        session.close();
        conn.close();
        //System.out.println(messageRecv);
        if(hasMessage) {
            return messageRecv;
        }
        else{
            return "nothing";
        }
    }


}