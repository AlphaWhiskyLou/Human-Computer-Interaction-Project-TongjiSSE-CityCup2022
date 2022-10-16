package
        com.banking.projectserver.controller;

import cn.hutool.jwt.JWTUtil;
import com.banking.projectserver.aop.log.WebLog;
import com.banking.projectserver.entity.BankAccount;
import com.banking.projectserver.entity.SystemFunction;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.service.BankAccountService;
import com.banking.projectserver.service.SystemFunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * TODO:基础业务接口controller
 *
 * @author 孟繁霖
 * @date 2021/11/22 23:29
 */
@Slf4j
@RestController
@RequestMapping("coreBusiness")
public class BasicBusinessController {
    @Autowired
    private SystemFunctionService systemFunctionService;

    @Autowired
    private BankAccountService bankAccountService;

    @WebLog
    @GetMapping("/getSystemFunction")
    public Response getSystemFunction(){
        List<SystemFunction> sfList = systemFunctionService.getSystemFunction();
        if(sfList == null) {
            return Response.unknownError().message("系统功能暂不开放");
        }
        else{
            return Response.OK().data("systemFunction",sfList);
        }
    }

    @GetMapping("/getSystemFunction/{functionID}")
    public Response getSystemFunctionById(@PathVariable int functionID){
        SystemFunction systemFunction= systemFunctionService.getSystemFunctionById(functionID);
        if(systemFunction==null){
            return Response.unknownError().message("该功能暂不开放");
        }
        else{
            return Response.OK().data("systemFunction",systemFunction);
        }
    }

    @PostMapping("/addSystemFunction/{functionID}/{functionName}/{functionDescription}")
    public Response addSystemFunction(@PathVariable int functionID,@PathVariable String functionName,@PathVariable String functionDescription){
        if(systemFunctionService.addSystemFunction(functionID,functionName,functionDescription)) {
            return Response.OK().message("系统功能添加成功");
        }
        else {
            return Response.unknownError().message("系统功能添加失败");
        }
    }

    @DeleteMapping("/deleteSystemFunctionById/{functionID}")
    public Response deleteSystemFunctionById(@PathVariable int functionID){
        if(systemFunctionService.deleteSystemFunctionById(functionID)) {
            return Response.OK().message("系统功能删除成功");
        }
        else {
            return Response.unknownError().message("系统功能删除失败");
        }
    }

    /**
     * TODO:为用户开户
     *
     */
    @WebLog
    @PostMapping("/openAccount")
    public Response openAccount(@RequestParam String userID,@RequestParam String password){
        String accountID = bankAccountService.openAccount(userID,password);
        if(accountID!=null){
            return Response.OK().data("accountID",accountID);
        }
        else{
            return Response.unknownError().message("开户失败");
        }
    }


    /**
    * TODO:由用户id查找所有账号id,应该从token中或者cookie中获取用户id
    * @return : com.banking.projectserver.response.Response
    * @author Covenant
    * @date 2021-11-29 14:49
    */
    @PostMapping("getAllAccountId")
    public Response getAllAccountId(HttpServletRequest request){
        String token = request.getHeader("token");
        String userID = (String) JWTUtil.parseToken(token).getPayload("uid");
        List<BankAccount> bankAccountList =bankAccountService.getAllAccountByUserId(userID);
        return Response.OK().data("bankAccountList",bankAccountList);
    }


    /**
    *  由账号id获取账号余额等所有账号信息
    * @param accountId 账号id
    * @return : com.banking.projectserver.response.Response
    * @author Covenant
    * @date 2021-11-29 14:00
    */
    @PostMapping("getAccountInfoById")
    public Response getAccountInfoById(@RequestParam String accountId){
        BankAccount bankAccount = bankAccountService.getBankAccountInfoById(accountId);
        if(bankAccount==null){
            return Response.unknownError().message("无此账号");
        }
        return Response.OK().data("account",bankAccount);
    }


    /**
    * 存款
    * @param accountId 账号ID
    * @param money 存款金额
    * @return : com.banking.projectserver.response.Response
    * @author Covenant
    * @date 2021-11-29 15:44
    */
    @PostMapping("saveMoney")
    public Response saveMoney(@RequestParam String accountId, @RequestParam BigDecimal money,@RequestParam("password")String  password){
        BankAccount bankAccountInfoById = bankAccountService.getBankAccountInfoById(accountId);
        if(bankAccountInfoById!=null){
            if(!bankAccountInfoById.getAccountPassword().equals(password)){
                return Response.unknownError().message("密码错误！");
            }
        }
        bankAccountService.saveMoney(accountId, money);
        return Response.OK().message("存款成功");
    }

    /**
    * 取款
    * @param accountId 账号ID
    * @param money 取款金额，为正
    * @return : com.banking.projectserver.response.Response
    * @author Covenant
    * @date 2021-11-29 15:56
    */
    @PostMapping("getMoney")
    public Response getMoney(@RequestParam("accountId") String accountId,@RequestParam("money") BigDecimal money,@RequestParam("password")String  password) throws Exception {
        BankAccount bankAccountInfoById = bankAccountService.getBankAccountInfoById(accountId);
        if(bankAccountInfoById!=null) {
            if (!bankAccountInfoById.getAccountPassword().equals(password)) {
                return Response.unknownError().message("密码错误！");
            }
        }
        bankAccountService.getMoney(accountId,money);
        return Response.OK().message("取款成功");
    }


    /**
    * 转账
    * @param accountId 1
    * @param transferToId 2
    * @param money 3
    * @return : com.banking.projectserver.response.Response
    * @author Covenant
    * @date 2021-11-29 16:24
    */
    @PostMapping("transferMoney")
    public Response transferMoney(@RequestParam("accountId") String accountId,@RequestParam("password")String password, @RequestParam("transferToId") String transferToId,@RequestParam("money") BigDecimal money) throws Exception {
        BankAccount bankAccountInfoById = bankAccountService.getBankAccountInfoById(accountId);
        if(bankAccountInfoById!=null){
            if(!bankAccountInfoById.getAccountPassword().equals(password)){
                return Response.unknownError().message("密码错误！");
            }
        }
        BankAccount accountTo = bankAccountService.getBankAccountInfoById(transferToId);
        System.out.println(accountTo.toString());
        if(accountTo==null||accountTo.getIsReportLoss()==1){

            return Response.unknownError().message("转入账户不存在或已被冻结");
        }
        bankAccountService.transferMoney(accountId,transferToId,money);
        return Response.OK().message("转账成功");
    }

}
