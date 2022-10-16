package com.banking.projectserver.controller;

import com.banking.projectserver.aop.log.UtilityLog;
import com.banking.projectserver.entity.SystemFunction;
import com.banking.projectserver.entity.UtilityAccount;
import com.banking.projectserver.entity.UtilityAccountDisp;
import com.banking.projectserver.mapper.BankAccountMapper;
import com.banking.projectserver.mapper.UtilityMapper;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.service.UtilityService;
import com.banking.projectserver.util.AesEncryptUtil;
import com.cxytiandi.encrypt.springboot.annotation.Decrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @ program: Bank
 * @ description: 生活缴费相关接口Controller
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 17:19:09
 */

@RestController
@RequestMapping("/utility")
public class UtilityController {

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Autowired
    private UtilityMapper utilityMapper;

    @GetMapping("/getUtilityFunction")
    public Response getUtilityFunction() {
        List<SystemFunction> uList = utilityService.getUtilityFunction();
        if(uList == null) {
            return Response.unknownError().message("生活缴费功能暂不开放");
        }
        else{
            return Response.OK().data("utilityFunction",uList);
        }
    }

    @GetMapping("/getUtilityAccount")
    public Response getUtilityAccount() {
        List<UtilityAccountDisp> aList = utilityService.getUtilityAccount();
        if(aList == null) {
            return Response.unknownError().message("暂无生活缴费账户");
        }
        else{
            return Response.OK().data("utilityAccount",aList);
        }
    }


    @PostMapping("addUtilityAccount/{accountID}/{utilityID}/{userID}/{utilityBalance}")
    public Response addUtilityAccount(@PathVariable String accountID,@PathVariable int utilityID,@PathVariable String userID,@PathVariable double utilityBalance){
        if(utilityService.addUtilityAccount(accountID, utilityID, userID, utilityBalance)) {
            return Response.OK().message("生活缴费账户添加成功");
        }
        else {
            return Response.unknownError().message("生活缴费账户添加失败");
        }
    }

    /**
     * TODO：将用户信息的校验封装进后端接口
     * @param userID
     * @param utilityBalance
     * @param functionID
     * @return
     */
    @UtilityLog
    @PostMapping("utilityPayment")
    public Response utilityPayment(@RequestParam String userID,@RequestParam double utilityBalance,@RequestParam int functionID,@RequestParam String accountID,@RequestParam String password) throws Exception {
        String AccountID = AesEncryptUtil.desEncrypt(accountID);
        //String passWord = AesEncryptUtil.desEncrypt(password);
        String TrueAccountPwd = bankAccountMapper.getAccountPassword(AccountID);
        if(Objects.equals(TrueAccountPwd,password)) {
            if(utilityService.utilityPayment(userID, BigDecimal.valueOf(utilityBalance), functionID, AccountID)) {
                return Response.OK().message("生活缴费成功");
            }
        }
        return Response.invalidService().message("生活缴费失败");
    }

    @UtilityLog
    @GetMapping("getUtilityAccountById/{userID}")
    public Response getUtilityAccountById(@PathVariable String userID){
        UtilityAccount utilityAccount = utilityService.getUtilityAccountById(userID);
        if(utilityAccount == null) {
            return Response.unknownError().message("生活缴费账户暂不开放");
        }
        else{
            return Response.OK().data("utilityAccount",utilityAccount);
        }
    }

}

