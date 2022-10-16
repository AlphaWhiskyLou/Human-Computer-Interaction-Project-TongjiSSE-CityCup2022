package com.banking.projectserver.serviceImpl;

import com.banking.projectserver.entity.BankAccount;
import com.banking.projectserver.entity.TradeRecord;
import com.banking.projectserver.mapper.BankAccountMapper;
import com.banking.projectserver.mapper.TradeRecordMapper;
import com.banking.projectserver.service.BankAccountService;
import com.banking.projectserver.util.generateBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.banking.projectserver.util.generateBankAccount.getBankNumber;

/**
 * TODO:此处写BankAccountServiceImpl类的描述
 *
 * @author 孟繁霖
 * @date 2021/11/29 13:52
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {


    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Autowired
    private TradeRecordMapper tradeRecordMapper;


    @Override
    public BankAccount getBankAccountInfoById(String accountId) {
        return bankAccountMapper.getAccountInfoById(accountId);
    }

    @Override
    public List<BankAccount> getAllAccountByUserId(String userId) {
        return bankAccountMapper.getAllAccountByUserId(userId);
    }

    @Override
    public String openAccount(String userId, String password){
        //void openAccount(String accountId, String userId, Date openDate, BigDecimal openMoney, String accountPassword, BigDecimal balance, Integer isReportLoss);
        String accountId = getBankNumber("6");
        System.out.println(accountId);
        Date openDate = new Date(System.currentTimeMillis());
        bankAccountMapper.openAccount(accountId, userId, openDate, new BigDecimal(0), password, new BigDecimal(0), 0);
        return accountId;
    }

    @Override
    public String getAccountPassword(String accountId) {
        return bankAccountMapper.getAccountPassword(accountId);
    }

    /**
    * TODO: 存款（应该增加账号是否冻结的判断）
    * @param accountId 账号ID
    * @param money 存款数量
    * @return : java.lang.Integer
    * @author Covenant
    * @date 2021-11-29 15:05
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMoney(String accountId, BigDecimal money) {
        //加业务记录
        String recordID= UUID.randomUUID().toString();
        Date date = new Date(System.currentTimeMillis());
        TradeRecord tradeRecord = new TradeRecord(recordID,accountId,2,money,date,null);
        tradeRecordMapper.addTradeRecord(tradeRecord);
        //存款
         bankAccountMapper.updateMoney(accountId,money);
    }



    /**
    * TODO 取款（应该增加账号是否冻结的判断）
    * @param accountId 账号ID
    * @param money 取款金额，为正
    * @author Covenant
    * @date 2021-11-29 16:03
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getMoney(String accountId,BigDecimal money) throws Exception {
        //检查余额是否够
        BankAccount accountInfo = bankAccountMapper.getAccountInfoById(accountId);
        if(accountInfo.getBalance().compareTo(money)==-1){
            throw new Exception("余额不足");
        }
        //加业务记录
        String recordID= UUID.randomUUID().toString();
        Date date = new Date(System.currentTimeMillis());
        TradeRecord tradeRecord = new TradeRecord(recordID,accountId,8,money,date,null);
        tradeRecordMapper.addTradeRecord(tradeRecord);
        //取款
        bankAccountMapper.updateMoney(accountId,money.negate());
    }

    /**
    * 转账
    * @param accountId 自身账户
    * @param transferToId 转入账户
    * @param money 转账金额
    * @author Covenant
    * @date 2021-11-29 16:22
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferMoney(String accountId,String transferToId, BigDecimal money) throws Exception {
        //检查自身余额是否足够
        BankAccount accountInfo = bankAccountMapper.getAccountInfoById(accountId);
        if(accountInfo.getBalance().compareTo(money)==-1){
            throw new Exception("余额不足");
        }
        //检查对方账户是否存在/可用
        BankAccount transferToAccount = bankAccountMapper.getAccountInfoById(transferToId);
        if(transferToAccount==null){
            throw new Exception("对方账户不存在/不可用");
        }
        //自身转出
        bankAccountMapper.updateMoney(accountId,money.negate());
        //对方转入
        bankAccountMapper.updateMoney(transferToId,money);
        //添加交易记录
        String recordID= UUID.randomUUID().toString();
        Date date = new Date(System.currentTimeMillis());
        TradeRecord tradeRecord = new TradeRecord(recordID,accountId,1,money,date,transferToId);
        tradeRecordMapper.addTradeRecord(tradeRecord);
    }


}
