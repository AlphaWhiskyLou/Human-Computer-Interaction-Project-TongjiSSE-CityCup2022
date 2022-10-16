package com.banking.projectserver.controller;

import com.banking.projectserver.entity.TradeRecord;
import com.banking.projectserver.entity.TradeRecordDisp;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.service.TradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :hp
 * @description :
 * @create :2021-11-29 14:19:00
 */
@RestController
@RequestMapping("/account")
public class TradeRecordController {
    @Autowired
    private TradeRecordService tradeRecordService;

    @GetMapping("/getTradeRecordByAccountID/{accountID}")
    public Response getTradeRecordByAccountID(@PathVariable String accountID){
        List<TradeRecordDisp> tradeRecordList = tradeRecordService.getTradeRecordByAccountID(accountID);
        if(tradeRecordList.equals(null)) {
            return Response.unknownError().message("暂无交易记录");
        }
        else{
            return Response.OK().data("tradeRecordList",tradeRecordList);
        }
    }
    @GetMapping("/getTradeRecordByRecordID/{recordID}")
    public Response getTradeRecordByRecordID(@PathVariable Integer recordID){
        TradeRecord tradeRecord = tradeRecordService.getTradeRecordByRecordID(recordID);
        if(tradeRecord.equals(null)) {
            return Response.unknownError().message("交易记录查询失败");
        }
        else{
            return Response.OK().data("tradeRecord",tradeRecord);
        }
    }
}
