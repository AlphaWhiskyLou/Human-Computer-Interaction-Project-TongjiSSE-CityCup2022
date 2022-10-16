package
        com.banking.projectserver.controller;

import cn.hutool.jwt.JWTUtil;
import com.banking.projectserver.aop.log.AssistanceLog;
import com.banking.projectserver.entity.*;
import com.banking.projectserver.mapper.BankAccountMapper;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.service.AssistanceService;
import com.banking.projectserver.service.SystemFunctionService;
import com.banking.projectserver.util.AesEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import javax.jms.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * TODO:子女协助相关接口controller
 *
 * @author 娄天宇
 * @date 2021/11/22 23:28
 */
@Slf4j
@RestController
@RequestMapping("assistance")
public class AssistanceController {

    @Autowired
    private SystemFunctionService systemFunctionService;

    @Autowired
    private AssistanceService assistanceService;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @AssistanceLog
    @GetMapping("getSystemFunctionById/{functionId}")
    public Response getSystemFunctionById(@PathVariable int functionId) {
        SystemFunction systemFunction = systemFunctionService.getSystemFunctionById(functionId);
        if (systemFunction == null) {
            return Response.unknownError().message("功能不存在");
        } else {
            return Response.OK().data("systemFunction", systemFunction);
        }
    }

    @AssistanceLog
    @GetMapping("getUnityIDByUser/{unityID}")
    public Response getUnityIDByUserID(HttpServletRequest request,@PathVariable String unityID) {
        String token = request.getHeader("token");
        String userID = (String) JWTUtil.parseToken(token).getPayload("uid");
        unityUser UnityID = assistanceService.getUnityUserByUserID(userID);
        if (UnityID.getUnityID().equals(unityID) && UnityID.getAuthorization() == 1) {
            return Response.OK().data("UnityID", UnityID);
        } else {
            return Response.invalidUser().message("用户不存在");
        }
    }

    /**
     * TODO:更新POST请求中的数据
     * TODO：需要添加密码验证！
     *
     * @param accountID
     * @param payeeAccountID
     * @param functionID
     * @param amount
     * @param isFinished
     * @return
     */
    @AssistanceLog
    @PostMapping("addAssistanceRequest")
    public Response addAssistanceRequest(@RequestParam String userID, @RequestParam String accountID, @RequestParam String payeeAccountID, @RequestParam int functionID, @RequestParam double amount, @RequestParam int isFinished, @RequestParam String password) {;
        String TrueAccountPwd = bankAccountMapper.getAccountPassword(accountID);
        if(functionID ==7){
            if (assistanceService.addAssistanceRequest(userID, accountID,payeeAccountID, functionID, amount, isFinished)) {
                return Response.OK().message("开户成功");
            }
        }
        if (functionID == 1 && (bankAccountMapper.getAccountInfoById(accountID) == null || bankAccountMapper.getAccountInfoById(payeeAccountID) == null)) {
            return Response.invalidUser().message("账户不存在-转账");
        }
        if(functionID!=7 && bankAccountMapper.getAccountInfoById(accountID) == null){
            return Response.invalidUser().message("账户不存在");
        }
        if (Objects.equals(TrueAccountPwd, password)) {
            if (assistanceService.addAssistanceRequest(userID, accountID,payeeAccountID, functionID, amount, isFinished)) {
                return Response.OK().message("添加成功");
            }
        } else {
            return Response.invalidService().message("添加失败");
        }
        return Response.invalidService().message("添加失败");
    }



    @AssistanceLog
    @PostMapping("updateAssistanceRequestStatus/{assistanceID}/{isFinished}")
    public Response updateAssistanceRequestStatus(@PathVariable String assistanceID, @PathVariable int isFinished) throws Exception {
        if (assistanceService.updateAssistanceRequestStatus(assistanceID, isFinished)) {
            return Response.OK().message("更新成功");
        } else {
            return Response.unknownError().message("更新失败");
        }
    }


    @AssistanceLog
    @GetMapping("getAssistanceRequestByUserID")
    public Response getAssistanceRequestByUserID(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userID = (String) JWTUtil.parseToken(token).getPayload("uid");
        List<AssistanceRequestDisp> aList = assistanceService.getAssistanceRequestByUserID(userID);
        if (aList == null) {
            return Response.unknownError().message("记录不存在");
        } else {
            return Response.OK().data("assistanceRequestDisp", aList);
        }
    }


    @AssistanceLog
    @GetMapping("getUnfinishedAssistanceRequestByUserID")
    public Response getUnfinishedAssistanceRequestByUserID(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userID = (String) JWTUtil.parseToken(token).getPayload("uid");
        List<AssistanceRequestDisp> aList = assistanceService.getUnfinishedAssistanceRequestByUserID(userID);
        if (aList == null) {
            return Response.unknownError().message("记录不存在");
        } else {
            return Response.OK().data("assistanceRequestDisp", aList);
        }
    }

    /*
    @AssistanceLog
    @PostMapping("generateMessage/{userID}")
    public Response generateMessage(@PathVariable String userID) throws JMSException {
        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"Message Sent");
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://106.14.25.240:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination dest = session.createQueue(userID);
        MessageProducer producer = session.createProducer(dest);
        Message msg = session.createTextMessage("MSG_" + (new Date()).toString());
        producer.send(msg);
        producer.close();
        session.close();
        connection.close();
        System.out.println("Assistance Message for " + userID+ " Sent.");
        return Response.OK();
    }

    @AssistanceLog
    @GetMapping("getMessage/{userID}")
    public Response getMessage(@PathVariable String userID) throws JMSException {
        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"Message Get");
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://106.14.25.240:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = session.createQueue(userID);
        MessageConsumer consumer = session.createConsumer(dest);
        int msgCount=0;
        Message msg = consumer.receive();
        TextMessage txtmsg = (TextMessage) msg;
        String txt = txtmsg.getText();
        System.out.println("Received Assistance Message from ActiveMQ: " + txt);
        if(txt != null) {
            msgCount=1;
        }
        consumer.close();
        session.close();
        connection.close();
        System.out.println("Completed.");
        if (msgCount > 0) {
            return Response.OK();
        }
        return Response.unknownError().message("记录不存在");
    }
    */
}
