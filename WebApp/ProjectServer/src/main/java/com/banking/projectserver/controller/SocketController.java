package
        com.banking.projectserver.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.serviceImpl.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.WebSocket;

/**
 * TODO:此处写SocketController类的描述
 *
 * @author 孟繁霖
 * @date 2021/12/23 20:17
 */
@RestController
public class SocketController {


    @Autowired
    private WebSocketService webSocketService;


    @MessageMapping("sendMessage")
    public Response sendMessage(@RequestBody String message){
        System.out.println(message);
        JSONObject jsonObject = JSONUtil.parseObj(message);
        String userName = (String)jsonObject.get("userName");
        String message1 = (String)jsonObject.get("message");
        System.out.println(userName+message1);
        webSocketService.sendToUser(userName,message1);
        return Response.OK().message("发送成功");
    }
}
