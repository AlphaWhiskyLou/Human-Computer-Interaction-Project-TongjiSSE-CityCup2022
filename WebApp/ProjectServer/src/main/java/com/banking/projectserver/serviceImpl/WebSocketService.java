package com.banking.projectserver.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO:此处写WebSocketServer类的描述
 *
 * @author 孟繁霖
 * @date 2021/12/12 19:55
 */
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 广播
     * @param msg 1
     * @author Covenant
     * @date 2021-12-12 21:00
     */
    public void sendMsg(String msg) {
        template.convertAndSend("topic/user", msg);
    }


    /**
    * TODO:点对点
    * @param username 1
    * @param msg 2
    * @author Covenant
    * @date 2021-12-23 20:30
    */
    public void sendToUser(String username,String msg){
        template.convertAndSendToUser(username,"/msg", msg);
    }

}
