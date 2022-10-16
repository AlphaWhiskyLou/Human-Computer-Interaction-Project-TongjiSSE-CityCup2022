package com.banking.projectserver.service;

import com.banking.projectserver.entity.BankAccount;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {

    BankAccount getBankAccountInfoById(String accountId);

    List<BankAccount> getAllAccountByUserId(String userId);
    void saveMoney(String accountId, BigDecimal money);

    void getMoney(String accountId,BigDecimal money) throws Exception;

    void transferMoney(String accountId,String transferToId,BigDecimal money) throws Exception;

    String openAccount(String userId, String password);

    String getAccountPassword(String accountId);
}
