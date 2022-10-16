package com.banking.projectserver.service;

import com.banking.projectserver.entity.SystemFunction;
import com.banking.projectserver.entity.UtilityAccount;
import com.banking.projectserver.entity.UtilityAccountDisp;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 17:59:14
 */

public interface UtilityService {
    List<SystemFunction> getUtilityFunction();
    List<UtilityAccountDisp> getUtilityAccount();
    UtilityAccount getUtilityAccountById(String userID);
    boolean addUtilityAccount(String accountID, int utilityID, String userID, double utilityBalance);
    boolean utilityPayment(String userID, BigDecimal payment, int functionID, String accountID) throws Exception;
}