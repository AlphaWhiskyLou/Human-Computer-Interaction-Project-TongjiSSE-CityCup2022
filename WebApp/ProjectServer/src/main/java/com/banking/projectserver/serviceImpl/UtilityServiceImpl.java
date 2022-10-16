package com.banking.projectserver.serviceImpl;

import com.banking.projectserver.entity.*;
import com.banking.projectserver.mapper.BankAccountMapper;
import com.banking.projectserver.mapper.TradeRecordMapper;
import com.banking.projectserver.mapper.UtilityMapper;
import com.banking.projectserver.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 18:08:46
 */
@Service
public class UtilityServiceImpl implements UtilityService{
    @Autowired
    private UtilityMapper utilityMapper;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Autowired
    private TradeRecordMapper tradeRecordMapper;

    @Override
    public List<SystemFunction> getUtilityFunction(){
        return utilityMapper.getUtilityFunction();
    }

    @Override
    public List<UtilityAccountDisp> getUtilityAccount(){
        return utilityMapper.getUtilityAccount();
    }

    @Override
    public UtilityAccount getUtilityAccountById(String userId){
        return utilityMapper.getUtilityAccountById(userId);
    }


    @Override
    public boolean addUtilityAccount(String accountID, int utilityID, String userID, double utilityBalance){
        utilityMapper.addUtilityAccount(accountID, utilityID, userID, utilityBalance);
        return true;
    }

    /**
     * TODO:生活缴费
     * @param userId
     * @param amount
     * @param functionId
     * @param accountID
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean utilityPayment(String userId, BigDecimal amount, int functionId, String accountID) throws Exception {
        BankAccount accountInfo = bankAccountMapper.getAccountInfoById(accountID);
        //System.out.println(bankAccountMapper.getAccountInfoById(accountID));
        if(accountInfo.getBalance().compareTo(amount) < 0){
            throw new Exception("余额不足");
        }
        bankAccountMapper.updateMoney(accountID, amount.negate());
        utilityMapper.utilityPayment(userId, amount, functionId);
        String recordId = UUID.randomUUID().toString();
        Date date = new Date(System.currentTimeMillis());
        TradeRecord tradeRecord = new TradeRecord(recordId, accountID, functionId, amount, date, "0");
        tradeRecordMapper.addTradeRecord(tradeRecord);
        return true;
    }
}