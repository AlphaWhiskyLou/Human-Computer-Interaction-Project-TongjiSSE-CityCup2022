package com.banking.projectserver.controller;

import com.banking.projectserver.aop.log.WebLog;
import com.banking.projectserver.entity.*;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.service.ConciergeService;
import com.banking.projectserver.service.SystemFunctionService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

/**
 * TODO:礼宾服务相关接口controller
 *
 * @author Ling Wang
 * @date 2021/11/22 23:30
 */
@RestController
@RequestMapping("concierge")
public class ConciergeController {
    @Autowired
    private ConciergeService conciergeService;
    @GetMapping("FreeRequest")
    public Response ConciergeRequestOnly(){
        List<Concierge> ConciergeList = conciergeService.ConciergeFreeRequest();
        if(ConciergeList.equals(null)) {
            return Response.unknownError().message("暂无交易记录");
        }
        else{
            return Response.OK().data("ConciergeList",ConciergeList);
        }
    }
    @PostMapping("ConciergeRequest")
    public Response ConciergeRequest(@RequestParam String gender, @RequestParam String language){
        List<Concierge> ConciergeList = conciergeService.ConciergeRequest(gender,language);
        if(ConciergeList.equals(null)) {
            return Response.unknownError().message("暂无交易记录");
        }
        else{
            return Response.OK().data("ConciergeList",ConciergeList);
        }
    }
    @GetMapping("ConRequest/{userID}")
    public Response ConciergeRequest(@PathVariable String userID){
        List<ConciergeRequest> ConciergeList = conciergeService.ConciergeInfoRequest(userID);
        System.out.println(ConciergeList);
        if(ConciergeList.equals(null)) {
            return Response.unknownError().message("暂无预定记录");
        }
        else{
            return Response.OK().data("ConciergeList",ConciergeList);
        }
    }
    @PostMapping("addConciergeRequest")
    public Response addConciergeRequest(@RequestParam String userID, @RequestParam String conciergeID, @RequestParam Date date){
        System.out.println(date);
        Date utilDate = new Date(System.currentTimeMillis());
        System.out.println(utilDate+" "+userID+" "+conciergeID);
        System.out.println(utilDate+" "+userID+" "+conciergeID+" "+date);
        UserConcierge userConcierge = new UserConcierge(userID,conciergeID,utilDate);
        if(conciergeService.addConciergeRequest(userConcierge)){
            return Response.OK().message("添加成功");
        }
        else {
            return Response.unknownError().message("添加失败");
        }
    }
    @GetMapping("selectConciergeRequest/{userID}")
    public Response selectConciergeRequest(@PathVariable String userID){
        int num= conciergeService.selectConciergeRequest(userID);
        return Response.OK().data("num",num);
    }

    @GetMapping("updateConciergeRequest/{userID}/{conciergeID}")
    public Response updateConciergeRequest(@PathVariable String userID,@PathVariable String conciergeID){
        if(conciergeService.updateConciergeRequest(userID,conciergeID)){
            return Response.OK().message("更新成功");
        }
        else {
            return Response.unknownError().message("更新失败");
        }
    }
    @GetMapping("deleteAssistanceRequestStatus/{userID}/{conciergeID}")
    public Response deleteConciergeRequest(@PathVariable String userID) {
        if(conciergeService.deleteConciergeRequest(userID)){
            return Response.OK().message("删除成功");
        }
        else {
            return Response.unknownError().message("删除失败");
        }
    }
}
